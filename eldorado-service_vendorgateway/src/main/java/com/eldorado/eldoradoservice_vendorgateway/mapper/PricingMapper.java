package com.eldorado.eldoradoservice_vendorgateway.mapper;

import com.eldorado.eldoradoservice_vendorgateway.config.AppConfig;
import com.eldorado.eldoradoservice_vendorgateway.models.HeaderDTO;
import com.eldorado.eldoradoservice_vendorgateway.models.PricingDTO;
import com.eldorado.eldoradoservice_vendorgateway.models.enums.MdEntryType;
import com.eldorado.eldoradoservice_vendorgateway.models.enums.Product;
import com.eldorado.eldoradoservice_vendorgateway.models.enums.QuoteCondition;
import com.eldorado.eldoradoservice_vendorgateway.models.marketdata.MarketDataBodyDTO;
import com.eldorado.eldoradoservice_vendorgateway.models.marketdata.MarketDataDTO;
import com.eldorado.eldoradoservice_vendorgateway.models.marketdata.MarketDataEntryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static com.eldorado.eldoradoservice_vendorgateway.models.enums.MsgType.MARKET_DATA;

@Component
@RequiredArgsConstructor
public class PricingMapper {

  private final AppConfig appConfig;

  public List<MarketDataDTO> mapToMarketData(List<PricingDTO> pricingDTOS) {
    return pricingDTOS.stream()
        .map(
            pricingDTO ->
                MarketDataDTO.builder()
                    .header(
                        HeaderDTO.builder()
                            .msgType(MARKET_DATA)
                            .senderCompId(appConfig.getVendorCompId())
                            .targetCompId(appConfig.getPlatformCompId())
                            .sendingTime(OffsetDateTime.now())
                            .build())
                    .body(
                        MarketDataBodyDTO.builder()
                            .symbol(pricingDTO.getSymbol())
                            .mdReqId(UUID.randomUUID())
                            .product(Product.COMMODITY)
                            .noMDEntries(
                                List.of(
                                    MarketDataEntryDTO.builder()
                                        .mdEntryType(MdEntryType.BID)
                                        .mdEntryPx(
                                            pricingDTO
                                                .getPrice()
                                                .multiply(
                                                    BigDecimal.ONE.add(appConfig.getCommission()))
                                                .setScale(2, RoundingMode.UP))
                                        .currency(appConfig.getCurrency())
                                        .mdEntrySize(pricingDTO.getQuantity())
                                        .quoteCondition(QuoteCondition.ACTIVE)
                                        .quoteEntryId(UUID.randomUUID())
                                        .mdEntryPositionNo(0)
                                        .mdDateTime(OffsetDateTime.now())
                                        .build(),
                                    MarketDataEntryDTO.builder()
                                        .mdEntryType(MdEntryType.OFFER)
                                        .mdEntryPx(
                                            pricingDTO
                                                .getPrice()
                                                .multiply(
                                                    BigDecimal.ONE.subtract(
                                                        appConfig.getCommission()))
                                                .setScale(2, RoundingMode.UP))
                                        .currency(appConfig.getCurrency())
                                        .mdEntrySize(pricingDTO.getQuantity())
                                        .quoteCondition(QuoteCondition.ACTIVE)
                                        .quoteEntryId(UUID.randomUUID())
                                        .mdEntryPositionNo(0)
                                        .mdDateTime(OffsetDateTime.now())
                                        .build()))
                            .build())
                    .build())
        .toList();
  }
}
