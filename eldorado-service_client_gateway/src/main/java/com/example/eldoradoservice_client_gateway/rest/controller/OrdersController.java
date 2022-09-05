package com.example.eldoradoservice_client_gateway.rest.controller;

import com.example.eldoradoservice_client_gateway.model.orders.ExecutionReport;
import com.example.eldoradoservice_client_gateway.model.orders.NewOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/order", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrdersController {

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ExecutionReport newOrder(@RequestBody NewOrderRequest newOrderRequest) {
    return null;
  }

  @GetMapping(path = "/{clOrdId}")
  public List<ExecutionReport> getExecutionReports(@PathVariable String clOrdId) {
    return null;
  }
}
