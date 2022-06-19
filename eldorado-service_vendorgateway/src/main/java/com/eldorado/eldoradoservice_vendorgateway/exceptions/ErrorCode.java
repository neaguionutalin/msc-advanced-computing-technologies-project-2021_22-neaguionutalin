package com.eldorado.eldoradoservice_vendorgateway.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

  REQUEST_BODY_VALIDATION_FAILURE("VENDORGATEWAY_0001");

  private final String code;

}
