package com.example.eldoradoservice_client_gateway.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.example.eldoradoservice_client_gateway.exceptions.ErrorCode.USER_ALREADY_EXISTS_EXCEPTION;

@Getter
@Slf4j
public class UserAlreadyExistsException extends ClientGatewayException {
  private final String email;

  public UserAlreadyExistsException(String email) {
    this.email = email;
    setErrorCode(USER_ALREADY_EXISTS_EXCEPTION);
    log.error(
        "{}:{}: For email {}",
        USER_ALREADY_EXISTS_EXCEPTION.getCode(),
        USER_ALREADY_EXISTS_EXCEPTION.name(),
        email);
  }
}
