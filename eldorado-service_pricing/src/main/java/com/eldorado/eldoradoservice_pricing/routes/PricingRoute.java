package com.eldorado.eldoradoservice_pricing.routes;

import com.eldorado.eldoradoservice_pricing.config.RouteConfigs.RouteConfig;
import com.eldorado.eldoradoservice_pricing.models.marketdata.MarketDataDTO;
import com.eldorado.eldoradoservice_pricing.services.PricingService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PricingRoute extends RouteBuilder {

  private final RouteConfig priceImport;
  private final PricingService pricingService;

  @Override
  public void configure() {
    from(priceImport.getFrom())
        .unmarshal()
        .json(JsonLibrary.Jackson, MarketDataDTO.class)
        .bean(pricingService);
  }
}
