package com.example.eldoradoservice_client_gateway.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProductConverter implements AttributeConverter<Product, String> {
  @Override
  public String convertToDatabaseColumn(Product attribute) {
    if (attribute == null) return null;
    return attribute.getCode();
  }

  @Override
  public Product convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;
    return Stream.of(Product.values())
        .filter(c -> c.getCode().equals(dbData))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
  }
}
