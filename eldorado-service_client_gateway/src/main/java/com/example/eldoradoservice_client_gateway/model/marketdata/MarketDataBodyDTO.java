package com.example.eldoradoservice_client_gateway.model.marketdata;

import com.example.eldoradoservice_client_gateway.model.enums.Product;
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
