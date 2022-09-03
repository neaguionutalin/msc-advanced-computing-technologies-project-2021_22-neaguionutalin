package com.example.eldoradoservice_client_gateway.exceptions;

import lombok.Getter;

import static com.example.eldoradoservice_client_gateway.exceptions.ErrorCode.INSTITUTION_NOT_EXIST_EXCEPTION;

@Getter
public class InstitutionDoesNotExistException extends ClientGatewayException {

  private final String compId;

  public InstitutionDoesNotExistException(String compId) {
    this.compId = compId;
    setErrorCode(INSTITUTION_NOT_EXIST_EXCEPTION);
  }
}
