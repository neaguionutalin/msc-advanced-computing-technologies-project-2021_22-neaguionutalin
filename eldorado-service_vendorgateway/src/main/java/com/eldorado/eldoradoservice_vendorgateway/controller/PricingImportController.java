package com.eldorado.eldoradoservice_vendorgateway.controller;

import com.eldorado.eldoradoservice_vendorgateway.service.PricingImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PricingImportController {

  private final PricingImportService pricingImportService;

  @Scheduled(cron = "*/5 * * * * ?")
  public void importPricing() {
    pricingImportService.importPrices();
  }
}
