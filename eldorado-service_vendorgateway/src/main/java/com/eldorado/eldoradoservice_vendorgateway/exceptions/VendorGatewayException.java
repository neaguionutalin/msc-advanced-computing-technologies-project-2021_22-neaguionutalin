package com.eldorado.eldoradoservice_vendorgateway.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public abstract class VendorGatewayException extends RuntimeException {

  private ErrorCode errorCode;

  public VendorGatewayException(Throwable throwable) {
    super(throwable);
  }
}
