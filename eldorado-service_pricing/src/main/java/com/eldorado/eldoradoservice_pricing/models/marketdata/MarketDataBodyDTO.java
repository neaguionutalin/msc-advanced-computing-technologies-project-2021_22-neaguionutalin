package com.eldorado.eldoradoservice_pricing.models.marketdata;

import com.eldorado.eldoradoservice_pricing.models.enums.Product;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MarketDataBodyDTO {

  private String symbol;
  private UUID mdReqId;
  private Product product;
  private List<MarketDataEntryDTO> noMDEntries;
}
