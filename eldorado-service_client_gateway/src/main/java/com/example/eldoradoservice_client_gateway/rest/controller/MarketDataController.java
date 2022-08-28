package com.example.eldoradoservice_client_gateway.rest.controller;

import com.example.eldoradoservice_client_gateway.model.enums.MarketDataType;
import com.example.eldoradoservice_client_gateway.model.marketdata.MarketDataDTO;
import com.example.eldoradoservice_client_gateway.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/marketdata")
public class MarketDataController {

  private final MarketDataService marketDataService;

  @GetMapping(path = "/{marketDataType}/{symbol}", produces = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('BROKER')")
  public MarketDataDTO marketDataDTO(
      @PathVariable MarketDataType marketDataType, @PathVariable String symbol) {
    return marketDataService.getMarketData(marketDataType, symbol);
  }
}
