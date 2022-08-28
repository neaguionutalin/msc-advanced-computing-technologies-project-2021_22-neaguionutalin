package com.example.eldoradoservice_client_gateway.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  USER_ALREADY_EXISTS_EXCEPTION("CG_0001"),
  USER_NOT_REGISTERED_EXCEPTION("CG_0002"),
  INCORRECT_PASSWORD_EXCEPTION("CG_0003"),
  MARKET_DATA_UNAVAILABLE_EXCEPTION("CG_0004"),
  UNAUTHORISED_EXCEPTION("CG_0005");

  private final String code;
}
