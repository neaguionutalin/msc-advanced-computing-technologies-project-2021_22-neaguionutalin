package com.example.eldoradoservice_client_gateway.service;

import com.example.eldoradoservice_client_gateway.exceptions.MarketDataServiceUnavailableException;
import com.example.eldoradoservice_client_gateway.model.BidAndOffer;
import com.example.eldoradoservice_client_gateway.model.HeaderDTO;
import com.example.eldoradoservice_client_gateway.model.RedisMarketData;
import com.example.eldoradoservice_client_gateway.model.enums.MarketDataType;
import com.example.eldoradoservice_client_gateway.model.enums.QuoteCondition;
import com.example.eldoradoservice_client_gateway.model.marketdata.MarketDataBodyDTO;
import com.example.eldoradoservice_client_gateway.model.marketdata.MarketDataDTO;
import com.example.eldoradoservice_client_gateway.model.marketdata.MarketDataEntryDTO;
import com.example.eldoradoservice_client_gateway.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.eldoradoservice_client_gateway.model.enums.MarketDataType.TOP;
import static com.example.eldoradoservice_client_gateway.model.enums.MdEntryType.BID;
import static com.example.eldoradoservice_client_gateway.model.enums.MdEntryType.OFFER;
import static com.example.eldoradoservice_client_gateway.model.enums.MsgType.MARKET_DATA;
import static com.example.eldoradoservice_client_gateway.model.enums.Product.COMMODITY;

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
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        (UsernamePasswordAuthenticationToken)
            SecurityContextHolder.getContext().getAuthentication();
    ResponseEntity<RedisMarketData> marketDataResponseEntity =
        restTemplate.getForEntity(
            String.format("%s%s/%s/%s", baseUrl, path, marketDataType.name(), symbol),
            RedisMarketData.class);
    if (marketDataResponseEntity.getStatusCode() != HttpStatus.OK)
      throw new MarketDataServiceUnavailableException();
    List<MarketDataEntryDTO> mdEntries = new ArrayList<>();
    Objects.requireNonNull(marketDataResponseEntity.getBody())
        .setBids(
            Objects.requireNonNull(marketDataResponseEntity.getBody()).getBids().stream()
                .sorted()
                .collect(Collectors.toList()));
    for (int i = 0;
        i < Objects.requireNonNull(marketDataResponseEntity.getBody()).getBids().size();
        i++) {
      BidAndOffer bid = Objects.requireNonNull(marketDataResponseEntity.getBody()).getBids().get(i);

      mdEntries.add(
          MarketDataEntryDTO.builder()
              .mdEntryType(BID)
              .mdEntryPx(bid.getPrice())
              .currency("USD")
              .mdEntrySize(bid.getDepth())
              .quoteCondition(QuoteCondition.valueOf(bid.getQuoteCondition().name()))
              .quoteEntryId(bid.getGdxMdEntryId())
              .mdEntryPositionNo(marketDataType == TOP ? 0 : i + 1)
              .mdDateTime(OffsetDateTime.now())
              .build());
    }

    Objects.requireNonNull(marketDataResponseEntity.getBody())
        .setOffers(
            Objects.requireNonNull(marketDataResponseEntity.getBody()).getOffers().stream()
                .sorted()
                .collect(Collectors.toList()));
    for (int i = Objects.requireNonNull(marketDataResponseEntity.getBody()).getOffers().size() - 1;
        i >= 0;
        i--) {
      BidAndOffer offer =
          Objects.requireNonNull(marketDataResponseEntity.getBody()).getOffers().get(i);

      mdEntries.add(
          MarketDataEntryDTO.builder()
              .mdEntryType(OFFER)
              .mdEntryPx(offer.getPrice())
              .currency("USD")
              .mdEntrySize(offer.getDepth())
              .quoteCondition(QuoteCondition.valueOf(offer.getQuoteCondition().name()))
              .quoteEntryId(offer.getGdxMdEntryId())
              .mdEntryPositionNo(marketDataType == TOP ? 0 : i + 1)
              .mdDateTime(OffsetDateTime.now())
              .build());
    }

    return MarketDataDTO.builder()
        .header(
            HeaderDTO.builder()
                .msgType(MARKET_DATA)
                .senderCompId(compId)
                .targetCompId(
                    ((User) usernamePasswordAuthenticationToken.getPrincipal())
                        .getInstitution()
                        .getFriendlyCompId())
                .sendingTime(OffsetDateTime.now())
                .build())
        .body(
            MarketDataBodyDTO.builder()
                .symbol(symbol)
                .mdReqId(UUID.randomUUID())
                .product(COMMODITY)
                .noMDEntries(mdEntries)
                .build())
        .build();
  }
}
