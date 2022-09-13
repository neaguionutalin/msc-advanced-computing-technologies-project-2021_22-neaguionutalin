package com.eldorado.eldoradosercice_oms.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrdTypeConverter implements AttributeConverter<OrdType, String> {
  @Override
  public String convertToDatabaseColumn(OrdType attribute) {
    if (attribute == null) return null;
    return attribute.getCode();
  }

  @Override
  public OrdType convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    return Stream.of(OrdType.values())
        .filter(c -> c.getCode().equals(dbData))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
