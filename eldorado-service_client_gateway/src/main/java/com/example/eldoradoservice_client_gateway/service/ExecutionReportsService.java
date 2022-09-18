package com.example.eldoradoservice_client_gateway.service;

import com.example.eldoradoservice_client_gateway.exceptions.OrderDoesNotExistException;
import com.example.eldoradoservice_client_gateway.model.orders.ExecutionReport;
import com.example.eldoradoservice_client_gateway.model.orders.ExecutionReportBody;
import com.example.eldoradoservice_client_gateway.repository.entity.Execution;
import com.example.eldoradoservice_client_gateway.repository.entity.Order;
import com.example.eldoradoservice_client_gateway.repository.entity.User;
import com.example.eldoradoservice_client_gateway.repository.repo.ExecutionReportsRepository;
import com.example.eldoradoservice_client_gateway.repository.repo.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExecutionReportsService {
  private final OrdersRepository ordersRepository;
  private final ExecutionReportsRepository executionReportsRepository;

  @Handler
  public void handle(@Body ExecutionReport executionReport) {
    Optional<Order> orderOptional =
        ordersRepository.findByClOrdIdAndSenderCompId(
            executionReport.getBody().getClOrdId(), executionReport.getHeader().getTargetCompId());
    if (orderOptional.isEmpty())
      throw new OrderDoesNotExistException(executionReport.getBody().getClOrdId());
    orderOptional =
        Optional.of(
            orderOptional.get().toBuilder()
                .leavesQty(executionReport.getBody().getLeavesQty())
                .ordStatus(executionReport.getBody().getOrdStatus())
                .modifiedOn(OffsetDateTime.now())
                .build());
    ordersRepository.saveAndFlush(orderOptional.get());
    Execution execution =
        Execution.builder()
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
    executionReportsRepository.saveAndFlush(execution);
  }

  public List<ExecutionReportBody> getExecutionReports(String clOrdId) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        (UsernamePasswordAuthenticationToken)
            SecurityContextHolder.getContext().getAuthentication();
    List<Execution> executions =
        executionReportsRepository.findAllByClOrdIdAndTargetCompId(
            clOrdId,
            ((User) usernamePasswordAuthenticationToken.getPrincipal())
                .getInstitution()
                .getFriendlyCompId());
    return executions.stream()
        .map(
            t ->
                ExecutionReportBody.builder()
                    .avgPx(t.getAvgPx())
                    .clOrdId(t.getClOrdId())
                    .orderId(t.getOrderId())
                    .cumQty(t.getCumQty())
                    .currency(t.getCurrency())
                    .execId(t.getExecId())
                    .lastPx(t.getLastPx())
                    .lastQty(t.getLastQty())
                    .orderQty(t.getOrderQty())
                    .ordStatus(t.getOrdStatus())
                    .ordType(t.getOrdType())
                    .side(t.getSide())
                    .symbol(t.getSymbol())
                    .text(t.getText())
                    .transactTime(t.getTransactTime())
                    .execType(t.getExecType())
                    .leavesQty(t.getLeavesQty())
                    .product(t.getProduct())
                    .build())
        .collect(Collectors.toList());
  }
}
