package com.example.eldoradoservice_client_gateway.exceptions;

import static com.example.eldoradoservice_client_gateway.exceptions.ErrorCode.MARKET_DATA_UNAVAILABLE_EXCEPTION;

public class MarketDataServiceUnavailableException extends ClientGatewayException {

  public MarketDataServiceUnavailableException() {
    setErrorCode(MARKET_DATA_UNAVAILABLE_EXCEPTION);
  }
}
