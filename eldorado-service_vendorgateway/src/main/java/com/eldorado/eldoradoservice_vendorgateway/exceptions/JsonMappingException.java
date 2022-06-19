package com.eldorado.eldoradoservice_vendorgateway.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonMappingException extends VendorGatewayException {

  public JsonMappingException(JsonProcessingException e) {
    super(e);
  }
}
