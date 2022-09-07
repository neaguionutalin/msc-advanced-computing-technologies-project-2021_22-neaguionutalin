package com.eldorado.eldoradosercice_oms.routes;

import com.eldorado.eldoradosercice_oms.config.RouteConfigs.RouteConfig;
import com.eldorado.eldoradosercice_oms.controller.NewOrderController;
import com.eldorado.eldoradosercice_oms.exceptions.NotEnoughPositionAvailable;
import com.eldorado.eldoradosercice_oms.model.ExecutionReport;
import com.eldorado.eldoradosercice_oms.model.NewOrder;
import com.eldorado.eldoradosercice_oms.service.OrderExceptionService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NewOrderRoute extends RouteBuilder {

  private final RouteConfig newOrder;
  private final NewOrderController newOrderService;
  private final OrderExceptionService orderExceptionService;

  @Override
  public void configure() throws Exception {
    onException(NotEnoughPositionAvailable.class)
        .handled(true)
        .bean(orderExceptionService, "handleNewOrderException")
        .marshal()
        .json(JsonLibrary.Jackson, ExecutionReport.class)
        .to(newOrder.getTo());

    from(newOrder.getFrom())
        .unmarshal()
        .json(JsonLibrary.Jackson, NewOrder.class)
        .bean(newOrderService)
        .choice()
        .when(header("order_created").isEqualTo(true))
        .marshal()
        .json(JsonLibrary.Jackson, ExecutionReport.class)
        .to(newOrder.getTo());
  }
}
