package com.example.eldoradoservice_client_gateway.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuoteCondition {
  ACTIVE("A"),
  INACTIVE("B");

  @JsonValue private final String code;
}
