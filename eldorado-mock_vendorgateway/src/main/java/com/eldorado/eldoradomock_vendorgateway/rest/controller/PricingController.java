package com.eldorado.eldoradomock_vendorgateway.rest.controller;

import com.eldorado.eldoradomock_vendorgateway.models.PricingDTO;
import com.eldorado.eldoradomock_vendorgateway.service.PricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PricingController {
  private final PricingService pricingService;

  /**
   * @param symbol optional query parameter to query by symbol
   * @return a list of all pricing objects
   */
  @GetMapping(path = "/api/v1/mock/pricing", produces = APPLICATION_JSON_VALUE)
  public List<PricingDTO> getPrices(
      @RequestParam(value = "symbol", required = false) Optional<String> symbol) {
    return pricingService.getPrices(symbol);
  }
}
