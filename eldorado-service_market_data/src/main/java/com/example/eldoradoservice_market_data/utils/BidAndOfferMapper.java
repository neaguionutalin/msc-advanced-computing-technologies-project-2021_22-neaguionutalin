package com.example.eldoradoservice_market_data.utils;

import com.example.eldoradoservice_market_data.models.BidAndOffer;
import com.example.eldoradoservice_market_data.models.enums.QuoteConditionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BidAndOfferMapper {

  public BidAndOffer mapBidAndOffer(String bidOffer) {
    String[] parts = bidOffer.split("#");
    return BidAndOffer.builder()
        .depth(new BigDecimal(parts[0]))
        .price(new BigDecimal(parts[1]))
        .gdxMdEntryId(UUID.fromString(parts[2]))
        .quoteCondition(QuoteConditionType.valueOf(parts[3]))
        .datetime(DateFormat.parse(parts[4]))
        .friendlyCompId(parts[5])
        .build();
  }
}
