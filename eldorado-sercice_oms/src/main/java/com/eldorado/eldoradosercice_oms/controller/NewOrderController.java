package com.eldorado.eldoradosercice_oms.controller;

import com.eldorado.eldoradosercice_oms.adapter.MarketDataAdapter;
import com.eldorado.eldoradosercice_oms.model.ExecutionReport;
import com.eldorado.eldoradosercice_oms.model.NewOrder;
import com.eldorado.eldoradosercice_oms.model.RedisMarketData;
import com.eldorado.eldoradosercice_oms.repository.entities.Execution;
import com.eldorado.eldoradosercice_oms.repository.repo.ExecutionReportsRepository;
import com.eldorado.eldoradosercice_oms.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NewOrderController {

  private final MarketDataAdapter marketDataAdapter;
  private final PositionAvailabilityService positionAvailabilityService;
  private final OrderSplitService orderSplitService;
  private final OrderPersistenceService orderPersistenceService;
  private final OrderPublisherService orderPublisherService;
  private final NewOrderMapper newOrderMapper;
  private final ExecutionReportsRepository executionReportsRepository;

  @Handler
  public void handle(@Body NewOrder newOrder, Exchange exchange) {
    positionAvailabilityService.checkAvailablePosition(newOrder);
    RedisMarketData redisMarketData = marketDataAdapter.getMarketData(newOrder);
    List<NewOrder> newOrderList = orderSplitService.createNewOrders(newOrder, redisMarketData);
    orderPersistenceService.saveOrders(newOrderList, newOrder);
    orderPublisherService.publishOrders(newOrderList);
    ExecutionReport executionReport = newOrderMapper.mapToExecutionReport(newOrder);
    Execution execution = newOrderMapper.mapToExecutionReport(executionReport);
    executionReportsRepository.saveAndFlush(execution);
    exchange.getIn().setHeader("order_created", true);
    exchange.getIn().setBody(executionReport);
  }
}
