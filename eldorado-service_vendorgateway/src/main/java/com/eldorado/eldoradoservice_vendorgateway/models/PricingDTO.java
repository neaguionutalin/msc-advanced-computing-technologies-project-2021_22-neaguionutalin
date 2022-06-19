package com.eldorado.eldoradoservice_vendorgateway.models;

import lombok.*;

import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PricingDTO {
    private String symbol;
    private BigDecimal price;
    private BigDecimal quantity;
}
