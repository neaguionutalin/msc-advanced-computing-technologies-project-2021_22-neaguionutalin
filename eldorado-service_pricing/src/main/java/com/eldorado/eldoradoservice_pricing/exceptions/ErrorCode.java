package com.eldorado.eldoradoservice_pricing.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  JSON_MAPPING_EXCEPTION("PRICING_0000");

  private final String code;
}
