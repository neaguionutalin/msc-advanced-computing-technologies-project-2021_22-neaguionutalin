package com.example.eldoradoservice_client_gateway.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuoteCondition {
  ACTIVE("A"),
  INACTIVE("B");

  private final String code;
}
