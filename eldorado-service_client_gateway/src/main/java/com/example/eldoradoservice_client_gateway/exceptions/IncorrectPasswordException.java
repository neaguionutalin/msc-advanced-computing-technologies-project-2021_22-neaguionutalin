package com.example.eldoradoservice_client_gateway.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.example.eldoradoservice_client_gateway.exceptions.ErrorCode.INCORRECT_PASSWORD_EXCEPTION;

@Getter
@Slf4j
public class IncorrectPasswordException extends ClientGatewayException {

  private final String email;

  public IncorrectPasswordException(String email) {
    this.email = email;
    setErrorCode(INCORRECT_PASSWORD_EXCEPTION);
    log.error(
        "{}:{}: For email {}",
        INCORRECT_PASSWORD_EXCEPTION.getCode(),
        INCORRECT_PASSWORD_EXCEPTION.name(),
        email);
  }
}
