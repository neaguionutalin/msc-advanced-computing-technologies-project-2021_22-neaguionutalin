package com.eldorado.eldoradosercice_oms.controller;

import com.eldorado.eldoradosercice_oms.exceptions.OrderNotFoundException;
import com.eldorado.eldoradosercice_oms.model.ExecutionReport;
import com.eldorado.eldoradosercice_oms.model.ExecutionReportBody;
import com.eldorado.eldoradosercice_oms.model.HeaderDTO;
import com.eldorado.eldoradosercice_oms.model.enums.ExecType;
import com.eldorado.eldoradosercice_oms.model.enums.MsgType;
import com.eldorado.eldoradosercice_oms.model.enums.OrdStatus;
import com.eldorado.eldoradosercice_oms.repository.entities.Execution;
import com.eldorado.eldoradosercice_oms.repository.entities.Order;
import com.eldorado.eldoradosercice_oms.repository.repo.ExecutionReportsRepository;
import com.eldorado.eldoradosercice_oms.repository.repo.OrdersRepository;
import com.eldorado.eldoradosercice_oms.service.NewOrderMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.eldorado.eldoradosercice_oms.model.enums.OrdStatus.*;

@Controller
@RequiredArgsConstructor
public class ExecutionsController {

  private final OrdersRepository ordersRepository;
  private final ExecutionReportsRepository executionReportsRepository;
  private final NewOrderMapper newOrderMapper;

  @Handler
  public void handle(@Body ExecutionReport executionReport, Exchange exchange) {
    Optional<Order> optionalChildOrder =
        ordersRepository.findByClOrdIdAndTargetCompId(
            executionReport.getBody().getClOrdId(), executionReport.getHeader().getSenderCompId());
    if (optionalChildOrder.isEmpty()) throw new OrderNotFoundException(executionReport);
    optionalChildOrder =
        Optional.of(
            optionalChildOrder.get().toBuilder()
                .leavesQty(executionReport.getBody().getLeavesQty())
                .ordStatus(executionReport.getBody().getOrdStatus())
                .avgPx(executionReport.getBody().getAvgPx())
                .lastQty(executionReport.getBody().getLastQty())
                .lastPx(executionReport.getBody().getLastPx())
                .orderId(executionReport.getBody().getOrderId())
                .build());
    Execution childExecution = newOrderMapper.mapToExecutionReport(executionReport);

    executionReportsRepository.saveAndFlush(childExecution);
    ordersRepository.saveAndFlush(optionalChildOrder.get());

    Order parentOrder = optionalChildOrder.get().getParentOrder();
    Set<OrdStatus> ordStatuses =
        parentOrder.getChildOrders().stream().map(Order::getOrdStatus).collect(Collectors.toSet());
    OrdStatus ordStatus;
    if (ordStatuses.contains(NEW)) {
      if (ordStatuses.contains(FILLED) || ordStatuses.contains(PARTIALLY_FILLED))
        ordStatus = PARTIALLY_FILLED;
      else ordStatus = NEW;
    } else if (ordStatuses.contains(PARTIALLY_FILLED)) ordStatus = PARTIALLY_FILLED;
    else if (ordStatuses.contains(FILLED)) ordStatus = FILLED;
    else ordStatus = REJECTED;

    BigDecimal avgPx;
    if (executionReport.getBody().getOrdStatus() == NEW
        || executionReport.getBody().getOrdStatus() == REJECTED) avgPx = parentOrder.getAvgPx();
    else
      avgPx =
          (parentOrder.getChildOrders().stream()
                  .map(t -> t.getAvgPx().multiply(t.getCumQty()).setScale(2, RoundingMode.DOWN))
                  .reduce(BigDecimal::add)
                  .orElse(BigDecimal.ZERO))
              .divide(
                  parentOrder.getChildOrders().stream()
                      .map(Order::getCumQty)
                      .reduce(BigDecimal::add)
                      .orElse(BigDecimal.ONE),
                  2,
                  RoundingMode.DOWN);
    parentOrder =
        parentOrder.toBuilder()
            .leavesQty(
                parentOrder.getChildOrders().stream()
                    .map(Order::getLeavesQty)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO))
            .cumQty(
                parentOrder.getChildOrders().stream()
                    .map(Order::getCumQty)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO))
            .ordStatus(ordStatus)
            .lastPx(executionReport.getBody().getLastPx())
            .lastQty(executionReport.getBody().getLastQty())
            .avgPx(avgPx)
            .build();
    ordersRepository.saveAndFlush(parentOrder);

    ExecutionReport parentExecutionReport =
        ExecutionReport.builder()
            .header(
                HeaderDTO.builder()
                    .sendingTime(OffsetDateTime.now())
                    .targetCompId(parentOrder.getSenderCompId())
                    .senderCompId(parentOrder.getTargetCompId())
                    .msgType(MsgType.EXECUTION_REPORT)
                    .build())
            .body(
                ExecutionReportBody.builder()
                    .avgPx(avgPx)
                    .clOrdId(parentOrder.getClOrdId())
                    .orderId(parentOrder.getOrderId())
                    .cumQty(parentOrder.getCumQty())
                    .currency(parentOrder.getCurrency())
                    .execId(UUID.randomUUID())
                    .lastPx(parentOrder.getLastPx())
                    .lastQty(parentOrder.getLastQty())
                    .orderQty(parentOrder.getOrderQty())
                    .ordStatus(parentOrder.getOrdStatus())
                    .ordType(parentOrder.getOrdType())
                    .side(parentOrder.getSide())
                    .symbol(parentOrder.getSymbol())
                    .text(executionReport.getBody().getText())
                    .transactTime(OffsetDateTime.now())
                    .execType(
                        ordStatus == NEW
                            ? ExecType.NEW
                            : ordStatus == REJECTED ? ExecType.REJECTED : ExecType.TRADE)
                    .leavesQty(parentOrder.getLeavesQty())
                    .product(parentOrder.getProduct())
                    .build())
            .build();
    Execution execution = newOrderMapper.mapToExecutionReport(parentExecutionReport);
    executionReportsRepository.saveAndFlush(execution);
    exchange.getIn().setBody(parentExecutionReport);
  }
}
