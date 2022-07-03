package com.eldorado.eldoradoservice_pricing.services;

import com.eldorado.eldoradoservice_pricing.models.marketdata.MarketDataDTO;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

  @Handler
  public void handle(@Body MarketDataDTO marketDataDTO) {

  }
}
