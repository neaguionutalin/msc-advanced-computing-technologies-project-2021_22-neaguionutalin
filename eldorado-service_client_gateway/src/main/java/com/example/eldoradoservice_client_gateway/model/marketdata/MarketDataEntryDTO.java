package com.example.eldoradoservice_client_gateway.model.marketdata;

import com.example.eldoradoservice_client_gateway.model.enums.MdEntryType;
import com.example.eldoradoservice_client_gateway.model.enums.QuoteCondition;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MarketDataEntryDTO {

  private MdEntryType mdEntryType;
  private BigDecimal mdEntryPx;
  private String currency;
  private BigDecimal mdEntrySize;
  private QuoteCondition quoteCondition;
  private UUID quoteEntryId;
  private Integer mdEntryPositionNo;
  private OffsetDateTime mdDateTime;
}
