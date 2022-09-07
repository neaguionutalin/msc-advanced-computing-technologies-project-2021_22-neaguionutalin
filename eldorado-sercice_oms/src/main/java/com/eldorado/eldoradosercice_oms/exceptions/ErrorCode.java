package com.eldorado.eldoradosercice_oms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  MARKET_DATA_EXCEPTION("OMS_0001"),
  NOT_ENOUGH_POSITION_AVAILABLE("OMS_0002");

  private final String errorCode;
}
