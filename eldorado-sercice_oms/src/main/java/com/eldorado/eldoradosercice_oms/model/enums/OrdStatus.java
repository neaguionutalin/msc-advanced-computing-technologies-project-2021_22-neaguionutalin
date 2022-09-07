package com.eldorado.eldoradosercice_oms.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrdStatus {
  NEW("0"),
  PARTIALLY_FILLED("1"),
  FILLED("2"),
  REJECTED("8");

  @JsonValue private final String code;
}
