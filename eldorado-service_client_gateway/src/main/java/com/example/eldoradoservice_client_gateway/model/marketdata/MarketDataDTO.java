package com.example.eldoradoservice_client_gateway.model.marketdata;

import com.example.eldoradoservice_client_gateway.model.HeaderDTO;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MarketDataDTO {

  private HeaderDTO header;
  private MarketDataBodyDTO body;
}
