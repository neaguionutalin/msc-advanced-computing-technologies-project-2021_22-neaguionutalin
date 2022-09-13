package com.example.eldoradoservice_client_gateway.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TimeInForceConverter implements AttributeConverter<TimeInForce, String> {
  @Override
  public String convertToDatabaseColumn(TimeInForce attribute) {
    if (attribute == null) return null;
    return attribute.getCode();
  }

  @Override
  public TimeInForce convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    return Stream.of(TimeInForce.values())
        .filter(c -> c.getCode().equals(dbData))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
