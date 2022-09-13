package com.eldorado.eldoradosercice_oms.service;

import com.eldorado.eldoradosercice_oms.config.AppConfig;
import com.eldorado.eldoradosercice_oms.model.ExecutionReport;
import com.eldorado.eldoradosercice_oms.model.ExecutionReportBody;
import com.eldorado.eldoradosercice_oms.model.HeaderDTO;
import com.eldorado.eldoradosercice_oms.model.NewOrder;
import com.eldorado.eldoradosercice_oms.model.enums.ExecType;
import com.eldorado.eldoradosercice_oms.model.enums.MsgType;
import com.eldorado.eldoradosercice_oms.model.enums.OrdStatus;
import com.eldorado.eldoradosercice_oms.repository.entities.Execution;
import com.eldorado.eldoradosercice_oms.repository.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NewOrderMapper {
  private final AppConfig appConfig;

  public Order mapToOrder(NewOrder newOrder, List<Order> childOrders) {
    return Order.builder()
        .id(UUID.randomUUID())
        .orderId(newOrder.getMetadata() != null ? newOrder.getMetadata().getOrderId() : null)
        .symbol(newOrder.getBody().getSymbol())
        .orderQty(newOrder.getBody().getOrderQty())
        .side(newOrder.getBody().getSide())
        .currency(newOrder.getBody().getCurrency())
        .product(newOrder.getBody().getProduct())
        .ordType(newOrder.getBody().getOrdType())
        .clOrdId(newOrder.getBody().getClOrdId())
        .timeInForce(newOrder.getBody().getTimeInForce())
        .transactTime(newOrder.getBody().getTransactTime())
        .leavesQty(newOrder.getBody().getOrderQty())
        .cumQty(BigDecimal.ZERO)
        .avgPx(BigDecimal.ZERO)
        .lastPx(BigDecimal.ZERO)
        .lastQty(BigDecimal.ZERO)
        .ordStatus(OrdStatus.NEW)
        .senderCompId(newOrder.getHeader().getSenderCompId())
        .targetCompId(newOrder.getHeader().getTargetCompId())
        .childOrders(childOrders)
        .createdOn(OffsetDateTime.now())
        .modifiedOn(OffsetDateTime.now())
        .build();
  }

  public ExecutionReport mapToExecutionReport(NewOrder newOrder) {
    return ExecutionReport.builder()
        .header(
            HeaderDTO.builder()
                .msgType(MsgType.EXECUTION_REPORT)
                .senderCompId(appConfig.getPlatformCompId())
                .targetCompId(newOrder.getHeader().getSenderCompId())
                .sendingTime(OffsetDateTime.now())
                .build())
        .body(
            ExecutionReportBody.builder()
                .avgPx(BigDecimal.ZERO)
                .clOrdId(newOrder.getBody().getClOrdId())
                .orderId(newOrder.getMetadata().getOrderId())
                .cumQty(BigDecimal.ZERO)
                .currency(newOrder.getBody().getCurrency())
                .execId(UUID.randomUUID())
                .lastPx(BigDecimal.ZERO)
                .lastQty(BigDecimal.ZERO)
                .ordStatus(OrdStatus.NEW)
                .ordType(newOrder.getBody().getOrdType())
                .side(newOrder.getBody().getSide())
                .symbol(newOrder.getBody().getSymbol())
                .transactTime(OffsetDateTime.now())
                .execType(ExecType.NEW)
                .leavesQty(newOrder.getBody().getOrderQty())
                .product(newOrder.getBody().getProduct())
                .build())
        .build();
  }

  public Execution mapToExecutionReport(ExecutionReport executionReport) {
    return Execution.builder()
        .execId(executionReport.getBody().getExecId())
        .avgPx(executionReport.getBody().getAvgPx())
        .clOrdId(executionReport.getBody().getClOrdId())
        .orderId(executionReport.getBody().getOrderId())
        .cumQty(executionReport.getBody().getCumQty())
        .currency(executionReport.getBody().getCurrency())
        .lastPx(executionReport.getBody().getLastPx())
        .lastQty(executionReport.getBody().getLastQty())
        .orderQty(executionReport.getBody().getOrderQty())
        .ordStatus(executionReport.getBody().getOrdStatus())
        .ordType(executionReport.getBody().getOrdType())
        .side(executionReport.getBody().getSide())
        .symbol(executionReport.getBody().getSymbol())
        .text(executionReport.getBody().getText())
        .transactTime(executionReport.getBody().getTransactTime())
        .execType(executionReport.getBody().getExecType())
        .leavesQty(executionReport.getBody().getLeavesQty())
        .product(executionReport.getBody().getProduct())
        .senderCompId(executionReport.getHeader().getSenderCompId())
        .targetCompId(executionReport.getHeader().getTargetCompId())
        .createdOn(OffsetDateTime.now())
        .modifiedOn(OffsetDateTime.now())
        .build();
  }
}
