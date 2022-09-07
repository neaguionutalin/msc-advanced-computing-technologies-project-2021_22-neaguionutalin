package com.eldorado.eldoradoservice_vendorgateway.routes;

import com.eldorado.eldoradoservice_vendorgateway.config.RouteConfigs.RouteConfig;
import com.eldorado.eldoradoservice_vendorgateway.exceptions.PriceNotFoundException;
import com.eldorado.eldoradoservice_vendorgateway.models.ExecutionReport;
import com.eldorado.eldoradoservice_vendorgateway.models.NewOrder;
import com.eldorado.eldoradoservice_vendorgateway.service.OrderExceptionService;
import com.eldorado.eldoradoservice_vendorgateway.service.OrderExecutionService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NewOrderRoute extends RouteBuilder {
  private final RouteConfig childOrderRoute;
  private final OrderExecutionService orderExecutionService;
  private final OrderExceptionService orderExceptionService;

  @Override
  public void configure() throws Exception {
    onException(PriceNotFoundException.class)
        .handled(true)
        .bean(orderExceptionService)
        .marshal()
        .json(JsonLibrary.Jackson, ExecutionReport.class)
        .to(childOrderRoute.getTo());

    from(childOrderRoute.getFrom())
        .unmarshal()
        .json(JsonLibrary.Jackson, NewOrder.class)
        .bean(orderExecutionService)
            .choice().when(header("order_completed").isEqualTo(true))
        .marshal()
        .json(JsonLibrary.Jackson, ExecutionReport.class)
        .to(childOrderRoute.getTo());
  }
}
