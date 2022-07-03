package com.eldorado.eldoradoservice_pricing.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonMappingException extends PricingException {
  public JsonMappingException(JsonProcessingException e) {
    super(e);
    setErrorCode(ErrorCode.JSON_MAPPING_EXCEPTION);
  }
}
