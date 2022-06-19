package com.eldorado.eldoradoservice_vendorgateway.models.marketdata;

import com.eldorado.eldoradoservice_vendorgateway.models.HeaderDTO;
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
