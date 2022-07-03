package com.eldorado.eldoradoservice_pricing.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperBuilder {

  public static ObjectMapper getObjectMapper() {
    return new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(
            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
            SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
        .disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
  }
}
