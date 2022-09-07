package com.eldorado.eldoradosercice_oms.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExecType {
  NEW("0"),
  REJECTED("8"),
  TRADE("F");

  @JsonValue private final String code;
}
