package com.example.eldoradoservice_market_data.models;

import com.example.eldoradoservice_market_data.models.enums.MarketDataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode
@ToString
@Setter
public class RedisMarketData {

  private final MarketDataType type;
  private final List<BidAndOffer> bids;
  private final List<BidAndOffer> offers;
}
