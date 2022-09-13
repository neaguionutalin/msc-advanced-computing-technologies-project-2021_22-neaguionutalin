package com.example.eldoradoservice_client_gateway.rest.controller;

import com.example.eldoradoservice_client_gateway.model.OrderAcceptedResponse;
import com.example.eldoradoservice_client_gateway.model.orders.ExecutionReportBody;
import com.example.eldoradoservice_client_gateway.model.orders.NewOrderBody;
import com.example.eldoradoservice_client_gateway.service.ExecutionReportsService;
import com.example.eldoradoservice_client_gateway.service.NewOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.eldoradoservice_client_gateway.model.enums.NewOrderStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/order", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrdersController {
  private final NewOrderService newOrderService;
  private final ExecutionReportsService executionReportsService;

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('BROKER')")
  public OrderAcceptedResponse newOrder(@RequestBody NewOrderBody newOrderBody) {
    newOrderService.createNewOrder(newOrderBody);
    return OrderAcceptedResponse.builder().status(CREATED).build();
  }

  @GetMapping(path = "/{clOrdId}")
  @PreAuthorize("hasRole('BROKER')")
  public List<ExecutionReportBody> getExecutionReports(@PathVariable String clOrdId) {
    return executionReportsService.getExecutionReports(clOrdId);
  }
}
