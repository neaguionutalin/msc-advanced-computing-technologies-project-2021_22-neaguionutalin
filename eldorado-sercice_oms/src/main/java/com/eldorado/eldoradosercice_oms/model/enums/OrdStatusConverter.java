package com.eldorado.eldoradosercice_oms.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrdStatusConverter implements AttributeConverter<OrdStatus, String> {
  @Override
  public String convertToDatabaseColumn(OrdStatus attribute) {
    if (attribute == null) return null;
    return attribute.getCode();
  }

  @Override
  public OrdStatus convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    return Stream.of(OrdStatus.values())
        .filter(c -> c.getCode().equals(dbData))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
