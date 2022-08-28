package com.example.eldoradoservice_client_gateway.model;

import com.example.eldoradoservice_client_gateway.model.enums.QuoteConditionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@Getter
@EqualsAndHashCode
@ToString
public class BidAndOffer {

  private final BigDecimal price;
  private final BigDecimal depth;
  private final String friendlyCompId;
  private final UUID gdxMdEntryId;
  private final QuoteConditionType quoteCondition;
  private final OffsetDateTime datetime;
}
