package com.eldorado.eldoradoservice_vendorgateway.service;

import com.eldorado.eldoradoservice_vendorgateway.config.AppConfig;
import com.eldorado.eldoradoservice_vendorgateway.exceptions.PriceNotFoundException;
import com.eldorado.eldoradoservice_vendorgateway.models.ExecutionReport;
import com.eldorado.eldoradoservice_vendorgateway.models.ExecutionReportBody;
import com.eldorado.eldoradoservice_vendorgateway.models.HeaderDTO;
import com.eldorado.eldoradoservice_vendorgateway.models.NewOrder;
import com.eldorado.eldoradoservice_vendorgateway.models.enums.ExecType;
import com.eldorado.eldoradoservice_vendorgateway.models.enums.MsgType;
import com.eldorado.eldoradoservice_vendorgateway.models.enums.OrdStatus;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderExceptionService {

  private final AppConfig appConfig;

  @Handler
  public void handle(Exchange exchange) {
    PriceNotFoundException priceNotFoundException =
        exchange.getProperty(Exchange.EXCEPTION_CAUGHT, PriceNotFoundException.class);
    NewOrder newOrder = priceNotFoundException.getNewOrder();
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
                    .avgPx(BigDecimal.ZERO)
                    .clOrdId(newOrder.getBody().getClOrdId())
                    .orderId(UUID.randomUUID())
                    .cumQty(BigDecimal.ZERO)
                    .currency(newOrder.getBody().getCurrency())
                    .execId(UUID.randomUUID())
                    .lastPx(BigDecimal.ZERO)
                    .lastQty(BigDecimal.ZERO)
                    .orderQty(newOrder.getBody().getOrderQty())
                    .ordStatus(OrdStatus.REJECTED)
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
