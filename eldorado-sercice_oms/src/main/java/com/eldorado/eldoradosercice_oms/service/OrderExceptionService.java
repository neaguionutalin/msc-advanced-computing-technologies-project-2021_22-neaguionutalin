package com.eldorado.eldoradosercice_oms.service;

import com.eldorado.eldoradosercice_oms.exceptions.OmsException;
import com.eldorado.eldoradosercice_oms.model.ExecutionReport;
import com.eldorado.eldoradosercice_oms.model.NewOrder;
import com.eldorado.eldoradosercice_oms.model.enums.OrdStatus;
import com.eldorado.eldoradosercice_oms.repository.entities.Execution;
import com.eldorado.eldoradosercice_oms.repository.entities.Order;
import com.eldorado.eldoradosercice_oms.repository.repo.ExecutionReportsRepository;
import com.eldorado.eldoradosercice_oms.repository.repo.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderExceptionService {

  private final NewOrderMapper newOrderMapper;
  private final ExecutionReportsRepository executionReportsRepository;
  private final OrdersRepository ordersRepository;

  public void handleNewOrderException(Exchange exchange) {
    OmsException omsException = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, OmsException.class);
    NewOrder newOrder = omsException.getNewOrder();
    Order order = newOrderMapper.mapToOrder(newOrder, null);
    order =
        order.toBuilder()
            .cumQty(BigDecimal.ZERO)
            .leavesQty(BigDecimal.ZERO)
            .ordStatus(OrdStatus.REJECTED)
            .build();
    ordersRepository.saveAndFlush(order);
    ExecutionReport executionReport = newOrderMapper.mapToRejectedExecutionReport(newOrder);
    Execution execution = newOrderMapper.mapToExecutionReport(executionReport);
    executionReportsRepository.saveAndFlush(execution);
    exchange.getIn().setBody(executionReport);
  }
}
