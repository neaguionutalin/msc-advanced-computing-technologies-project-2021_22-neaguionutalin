package com.eldorado.eldoradosercice_oms.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ExecTypeConverter implements AttributeConverter<ExecType, String> {
  @Override
  public String convertToDatabaseColumn(ExecType attribute) {
    if (attribute == null) return null;
    return attribute.getCode();
  }

  @Override
  public ExecType convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    return Stream.of(ExecType.values())
        .filter(c -> c.getCode().equals(dbData))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
