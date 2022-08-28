package com.eldorado.eldoradoservice_vendorgateway.service;

import com.eldorado.eldoradoservice_vendorgateway.config.AppConfig;
import com.eldorado.eldoradoservice_vendorgateway.config.RouteConfigs.RouteConfig;
import com.eldorado.eldoradoservice_vendorgateway.exceptions.BadRestResponse;
import com.eldorado.eldoradoservice_vendorgateway.mapper.PricingMapper;
import com.eldorado.eldoradoservice_vendorgateway.models.PricingDTO;
import com.eldorado.eldoradoservice_vendorgateway.models.marketdata.MarketDataDTO;
import com.eldorado.eldoradoservice_vendorgateway.utils.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PricingImportService {

  private final RestTemplate restTemplate;
  private final EventPublisher eventPublisher;
  private final AppConfig appConfig;
  private final PricingMapper pricingMapper;
  private final RouteConfig pricingSendingRoute;

  public void importPrices() {
    ResponseEntity<PricingDTO[]> responseEntity =
        restTemplate.getForEntity(
            String.format("%s%s", appConfig.getUri(), appConfig.getMarketData()),
            PricingDTO[].class);
    if (responseEntity.getStatusCode() != HttpStatus.OK) {
      throw new BadRestResponse(responseEntity.getStatusCode());
    }
    List<MarketDataDTO> marketDataDTO =
        pricingMapper.mapToMarketData(List.of(Objects.requireNonNull(responseEntity.getBody())));
    marketDataDTO.forEach(
        md ->
            eventPublisher.publish(pricingSendingRoute.getTo(), md, md.getBody().getMdReqId().toString()));
  }
}
