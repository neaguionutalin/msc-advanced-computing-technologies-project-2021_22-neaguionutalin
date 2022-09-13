package com.eldorado.eldoradosercice_oms.routes;

import com.eldorado.eldoradosercice_oms.config.RouteConfigs.RouteConfig;
import com.eldorado.eldoradosercice_oms.controller.ExecutionsController;
import com.eldorado.eldoradosercice_oms.model.ExecutionReport;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ExecutionReportRoute extends RouteBuilder {
  private final RouteConfig newOrder;
  private final RouteConfig childOrderRoute;
  private final ExecutionsController executionsController;

  @Override
  public void configure() {
    from(childOrderRoute.getFrom())
        .unmarshal()
        .json(JsonLibrary.Jackson, ExecutionReport.class)
        .bean(executionsController)
        .marshal()
        .json(JsonLibrary.Jackson, ExecutionReport.class)
        .to(newOrder.getTo());
  }
}
