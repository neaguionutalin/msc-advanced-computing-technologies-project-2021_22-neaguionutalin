package com.eldorado.eldoradosercice_oms.model;

import com.eldorado.eldoradosercice_oms.model.enums.QuoteConditionType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidAndOffer implements Comparable<BidAndOffer> {

  private BigDecimal price;
  private BigDecimal depth;
  private String friendlyCompId;
  private UUID gdxMdEntryId;
  private QuoteConditionType quoteCondition;
  private OffsetDateTime datetime;

  @Override
  public int compareTo(BidAndOffer o) {
    return this.price.compareTo(o.price);
  }
}
