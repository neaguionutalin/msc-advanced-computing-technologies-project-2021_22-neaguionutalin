package com.eldorado.eldoradosercice_oms.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Side {
  BUY("1"),
  SELL("2");

  @JsonValue private final String code;
}
