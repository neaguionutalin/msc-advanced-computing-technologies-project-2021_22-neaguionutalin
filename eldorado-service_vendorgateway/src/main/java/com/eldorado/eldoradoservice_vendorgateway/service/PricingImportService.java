package com.eldorado.eldoradoservice_vendorgateway.service;

import com.eldorado.eldoradoservice_vendorgateway.config.AppConfig;
import com.eldorado.eldoradoservice_vendorgateway.exceptions.BadRestResponse;
import com.eldorado.eldoradoservice_vendorgateway.models.PricingDTO;
import com.eldorado.eldoradoservice_vendorgateway.utils.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PricingImportService {

  private final RestTemplate restTemplate;
  private final EventPublisher eventPublisher;
  private final AppConfig appConfig;

  public void importPrices() {
    ResponseEntity<PricingDTO[]> responseEntity =
        restTemplate.getForEntity(
            String.format("%s%s", appConfig.getUri(), appConfig.getMarketData()),
            PricingDTO[].class);
    if (responseEntity.getStatusCode() != HttpStatus.OK) {
      throw new BadRestResponse(responseEntity.getStatusCode());
    }
    System.out.println(responseEntity.getBody());
  }
}
