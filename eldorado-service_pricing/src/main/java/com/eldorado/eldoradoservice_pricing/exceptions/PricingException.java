package com.eldorado.eldoradoservice_pricing.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public abstract class PricingException extends RuntimeException {

  private ErrorCode errorCode;

  public PricingException(Throwable throwable) {
    super(throwable);
  }
}
