package com.eldorado.eldoradosercice_oms.service;

import com.eldorado.eldoradosercice_oms.exceptions.OmsException;
import com.eldorado.eldoradosercice_oms.model.ExecutionReport;
import com.eldorado.eldoradosercice_oms.model.NewOrder;
import com.eldorado.eldoradosercice_oms.repository.entities.Execution;
import com.eldorado.eldoradosercice_oms.repository.repo.ExecutionReportsRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderExceptionService {

  private final NewOrderMapper newOrderMapper;
  private final ExecutionReportsRepository executionReportsRepository;

  public void handleNewOrderException(Exchange exchange) {
    OmsException omsException = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, OmsException.class);
    NewOrder newOrder = omsException.getNewOrder();
    ExecutionReport executionReport = newOrderMapper.mapToExecutionReport(newOrder);
    Execution execution = newOrderMapper.mapToExecutionReport(executionReport);
    executionReportsRepository.saveAndFlush(execution);
    exchange.getIn().setBody(executionReport);
  }
}
