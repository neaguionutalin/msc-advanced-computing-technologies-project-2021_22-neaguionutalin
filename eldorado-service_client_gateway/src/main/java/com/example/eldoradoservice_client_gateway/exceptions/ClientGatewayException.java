package com.example.eldoradoservice_client_gateway.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public abstract class ClientGatewayException extends RuntimeException {
  private ErrorCode errorCode;

  protected ClientGatewayException(Throwable throwable) {
    super(throwable);
  }
}
