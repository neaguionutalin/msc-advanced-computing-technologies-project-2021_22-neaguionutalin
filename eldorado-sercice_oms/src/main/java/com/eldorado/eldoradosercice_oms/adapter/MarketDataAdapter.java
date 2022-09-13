package com.eldorado.eldoradosercice_oms.adapter;

import com.eldorado.eldoradosercice_oms.exceptions.MarketDataException;
import com.eldorado.eldoradosercice_oms.model.NewOrder;
import com.eldorado.eldoradosercice_oms.model.RedisMarketData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MarketDataAdapter {

  private final RestTemplate restTemplate;

  @Value("${oms.market-data-url}")
  private String markerDataUrl;

  public RedisMarketData getMarketData(NewOrder newOrder) {
    ResponseEntity<RedisMarketData> marketDataResponseEntity =
        restTemplate.getForEntity(
            markerDataUrl + "/" + newOrder.getBody().getSymbol(), RedisMarketData.class);
    if (marketDataResponseEntity.getStatusCode() != HttpStatus.OK)
      throw new MarketDataException(newOrder);
    return marketDataResponseEntity.getBody();
  }
}
