package com.example.eldoradoservice_client_gateway.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MdEntryType {
  BID("0"),
  OFFER("1");
  private final String code;
}
