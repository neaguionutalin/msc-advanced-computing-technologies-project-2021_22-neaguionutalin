package com.eldorado.mock.vendor.models;

import lombok.*;

import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingDTO {
    private String symbol;
    private BigDecimal price;
    private BigDecimal quantity;
}
