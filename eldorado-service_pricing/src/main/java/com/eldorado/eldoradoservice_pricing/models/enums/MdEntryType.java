package com.eldorado.eldoradoservice_pricing.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MdEntryType {
  BID("0"),
  OFFER("1");
  private final String code;
}
