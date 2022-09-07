package com.eldorado.eldoradoservice_vendorgateway.service;

import com.eldorado.eldoradoservice_vendorgateway.config.AppConfig;
import com.eldorado.eldoradoservice_vendorgateway.exceptions.BadRestResponse;
import com.eldorado.eldoradoservice_vendorgateway.exceptions.PriceNotFoundException;
import com.eldorado.eldoradoservice_vendorgateway.models.*;
import com.eldorado.eldoradoservice_vendorgateway.models.enums.ExecType;
import com.eldorado.eldoradoservice_vendorgateway.models.enums.MsgType;
import com.eldorado.eldoradoservice_vendorgateway.models.enums.OrdStatus;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static com.eldorado.eldoradoservice_vendorgateway.models.enums.Side.BUY;

@Service
@RequiredArgsConstructor
public class OrderExecutionService {
  private final RestTemplate restTemplate;
  private final AppConfig appConfig;

  @Handler
  public void handle(@Body NewOrder newOrder, Exchange exchange) {
    ResponseEntity<PricingDTO[]> responseEntity =
        restTemplate.getForEntity(
            String.format("%s%s", appConfig.getUri(), appConfig.getMarketData()),
            PricingDTO[].class);
    if (responseEntity.getStatusCode() != HttpStatus.OK) {
      throw new BadRestResponse(responseEntity.getStatusCode());
    }
    BigDecimal price =
        Arrays.stream(Objects.requireNonNull(responseEntity.getBody()))
            .filter(t -> t.getSymbol().equals(newOrder.getBody().getSymbol()))
            .map(PricingDTO::getPrice)
            .findFirst()
            .orElseThrow(() -> new PriceNotFoundException(newOrder));
    exchange.getIn().setHeader("order_completed", true);
    if (newOrder.getBody().getSide() == BUY)
      price = price.add(price.multiply(appConfig.getCommission()).setScale(2, RoundingMode.DOWN));
    else
      price =
          price.subtract(price.multiply(appConfig.getCommission()).setScale(2, RoundingMode.DOWN));
    ExecutionReport executionReport =
        ExecutionReport.builder()
            .header(
                HeaderDTO.builder()
                    .msgType(MsgType.EXECUTION_REPORT)
                    .senderCompId(appConfig.getVendorCompId())
                    .targetCompId(appConfig.getPlatformCompId())
                    .sendingTime(OffsetDateTime.now())
                    .build())
            .body(
                ExecutionReportBody.builder()
                    .avgPx(price)
                    .clOrdId(newOrder.getBody().getClOrdId())
                    .orderId(UUID.randomUUID())
                    .cumQty(newOrder.getBody().getOrderQty())
                    .currency(newOrder.getBody().getCurrency())
                    .execId(UUID.randomUUID())
                    .lastPx(price)
                    .lastQty(newOrder.getBody().getOrderQty())
                    .orderQty(newOrder.getBody().getOrderQty())
                    .ordStatus(OrdStatus.FILLED)
                    .ordType(newOrder.getBody().getOrdType())
                    .side(newOrder.getBody().getSide())
                    .symbol(newOrder.getBody().getSymbol())
                    .transactTime(OffsetDateTime.now())
                    .execType(ExecType.TRADE)
                    .leavesQty(BigDecimal.ZERO)
                    .product(newOrder.getBody().getProduct())
                    .build())
            .build();
    exchange.getIn().setBody(executionReport);
  }
}
