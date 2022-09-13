package com.example.eldoradoservice_client_gateway.exceptions;

import lombok.Getter;

import static com.example.eldoradoservice_client_gateway.exceptions.ErrorCode.ORDER_DOES_NOT_EXIST_EXCEPTION;

@Getter
public class OrderDoesNotExistException extends ClientGatewayException {

  private final String clOrdId;

  public OrderDoesNotExistException(String clOrdId) {
    this.clOrdId = clOrdId;
    setErrorCode(ORDER_DOES_NOT_EXIST_EXCEPTION);
  }
}
