package com.example.eldoradoservice_client_gateway.routes;

import com.example.eldoradoservice_client_gateway.config.RouteConfigs.RouteConfig;
import com.example.eldoradoservice_client_gateway.model.orders.ExecutionReport;
import com.example.eldoradoservice_client_gateway.service.ExecutionReportsService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ExecutionReportsRoute extends RouteBuilder {

  private final RouteConfig newOrderRoute;
  private final ExecutionReportsService executionReportsService;

  @Override
  public void configure() {
    from(newOrderRoute.getFrom())
        .unmarshal()
        .json(JsonLibrary.Jackson, ExecutionReport.class)
        .bean(executionReportsService);
  }
}
