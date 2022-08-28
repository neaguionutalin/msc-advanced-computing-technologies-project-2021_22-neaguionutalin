package com.example.eldoradoservice_client_gateway.service;

import com.example.eldoradoservice_client_gateway.exceptions.MarketDataServiceUnavailableException;
import com.example.eldoradoservice_client_gateway.model.HeaderDTO;
import com.example.eldoradoservice_client_gateway.model.RedisMarketData;
import com.example.eldoradoservice_client_gateway.model.enums.MarketDataType;
import com.example.eldoradoservice_client_gateway.model.marketdata.MarketDataBodyDTO;
import com.example.eldoradoservice_client_gateway.model.marketdata.MarketDataDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;

import static com.example.eldoradoservice_client_gateway.model.enums.MsgType.MARKET_DATA;

@Service
@Slf4j
@RequiredArgsConstructor
public class MarketDataService {

  private final RestTemplate restTemplate;

  @Value("${cg.market-data.base-url}")
  private String baseUrl;

  @Value("${cg.market-data.path}")
  private String path;

  @Value("${cg.comp-id}")
  private String compId;

  public MarketDataDTO getMarketData(MarketDataType marketDataType, String symbol) {
    ResponseEntity<RedisMarketData> marketDataResponseEntity =
        restTemplate.getForEntity(
            String.format("%s%s/%s/%s", baseUrl, path, marketDataType.name(), symbol),
            RedisMarketData.class);
    if (marketDataResponseEntity.getStatusCode() != HttpStatus.OK)
      throw new MarketDataServiceUnavailableException();
    return MarketDataDTO.builder()
        .header(
            HeaderDTO.builder()
                .msgType(MARKET_DATA)
                .senderCompId(compId)
                .targetCompId(null) // TODO
                .sendingTime(OffsetDateTime.now())
                .build())
        .body(MarketDataBodyDTO.builder().build())
        .build();
  }
}
