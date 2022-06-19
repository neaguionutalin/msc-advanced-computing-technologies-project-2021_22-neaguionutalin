package com.eldorado.eldoradoservice_vendorgateway.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class BadRestResponse extends VendorGatewayException {
  public BadRestResponse(HttpStatus httpStatus) {
    log.error("Error status code: {}", httpStatus.value());
  }
}
