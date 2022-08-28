package com.example.eldoradoservice_client_gateway.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.example.eldoradoservice_client_gateway.exceptions.ErrorCode.USER_NOT_REGISTERED_EXCEPTION;

@Getter
@Slf4j
public class UserNotRegisteredException extends ClientGatewayException {
  private final String email;

  public UserNotRegisteredException(String email) {
    this.email = email;
    setErrorCode(USER_NOT_REGISTERED_EXCEPTION);
    log.error(
        "{}:{}: For email {}",
        USER_NOT_REGISTERED_EXCEPTION.getCode(),
        USER_NOT_REGISTERED_EXCEPTION.name(),
        email);
  }
}
