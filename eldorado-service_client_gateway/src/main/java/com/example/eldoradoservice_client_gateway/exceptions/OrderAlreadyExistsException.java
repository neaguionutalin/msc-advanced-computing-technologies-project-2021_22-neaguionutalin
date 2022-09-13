package com.example.eldoradoservice_client_gateway.exceptions;

import lombok.Getter;

import static com.example.eldoradoservice_client_gateway.exceptions.ErrorCode.ORDER_EXISTS_EXCEPTION;

@Getter
public class OrderAlreadyExistsException extends ClientGatewayException {

  private final String clOrdId;

  public OrderAlreadyExistsException(String clOrdId) {
    this.clOrdId = clOrdId;
    setErrorCode(ORDER_EXISTS_EXCEPTION);
  }
}
