package com.eldorado.eldoradoservice_vendorgateway.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Product {
  COMMODITY("2");

  @JsonValue private final String code;
}
