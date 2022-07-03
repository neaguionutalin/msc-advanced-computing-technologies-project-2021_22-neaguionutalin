package com.eldorado.eldoradoservice_pricing.models.marketdata;

import com.eldorado.eldoradoservice_pricing.models.HeaderDTO;
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
