package com.eldorado.eldoradoservice_pricing.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuoteCondition {
  ACTIVE("A"),
  INACTIVE("B");

  private final String code;
}
