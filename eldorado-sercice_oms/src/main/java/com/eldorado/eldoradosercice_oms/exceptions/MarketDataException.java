package com.eldorado.eldoradosercice_oms.exceptions;

import com.eldorado.eldoradosercice_oms.model.NewOrder;

import static com.eldorado.eldoradosercice_oms.exceptions.ErrorCode.MARKET_DATA_EXCEPTION;

public class MarketDataException extends OmsException {

  public MarketDataException(NewOrder newOrder) {
    setNewOrder(newOrder);
    setErrorCode(MARKET_DATA_EXCEPTION);
  }
}
