package com.example.eldoradoservice_client_gateway.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class SideConverter implements AttributeConverter<Side, String> {
  @Override
  public String convertToDatabaseColumn(Side attribute) {
    if (attribute == null) return null;
    return attribute.getCode();
  }

  @Override
  public Side convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    return Stream.of(Side.values())
        .filter(c -> c.getCode().equals(dbData))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
