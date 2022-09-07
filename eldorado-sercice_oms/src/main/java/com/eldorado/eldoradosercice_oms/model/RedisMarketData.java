package com.eldorado.eldoradosercice_oms.model;

import com.eldorado.eldoradosercice_oms.model.enums.MarketDataType;
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
