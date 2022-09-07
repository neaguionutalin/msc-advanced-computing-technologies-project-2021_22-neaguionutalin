package com.eldorado.eldoradosercice_oms.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonMappingException extends OmsException {
  public JsonMappingException(JsonProcessingException jsonProcessingException) {
    super(jsonProcessingException);
  }
}
