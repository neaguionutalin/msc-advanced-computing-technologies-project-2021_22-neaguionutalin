package com.example.eldoradoservice_client_gateway.model;

import com.example.eldoradoservice_client_gateway.model.enums.MarketDataType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RedisMarketData {

  private MarketDataType type;
  private List<BidAndOffer> bids;
  private List<BidAndOffer> offers;
}
