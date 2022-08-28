package com.example.eldoradoservice_client_gateway.exceptions;

import static com.example.eldoradoservice_client_gateway.exceptions.ErrorCode.UNAUTHORISED_EXCEPTION;

public class UnauthorisedException extends ClientGatewayException {

  public UnauthorisedException() {
    setErrorCode(UNAUTHORISED_EXCEPTION);
  }
}
