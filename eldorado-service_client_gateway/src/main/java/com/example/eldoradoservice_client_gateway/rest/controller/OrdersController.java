package com.example.eldoradoservice_client_gateway.rest.controller;

import com.example.eldoradoservice_client_gateway.model.orders.ExecutionReportBody;
import com.example.eldoradoservice_client_gateway.model.orders.NewOrderBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/order", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrdersController {

  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  public ExecutionReportBody newOrder(@RequestBody NewOrderBody newOrderBody) {
    return null;
  }

  @GetMapping(path = "/{clOrdId}")
  public List<ExecutionReportBody> getExecutionReports(@PathVariable String clOrdId) {
    return null;
  }
}
