package com.eldorado.eldoradomock_vendorgateway.repositories.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "MarketPrices")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingDbDTO {

    @Id
    private Integer id;
    private String symbol;
    private BigDecimal price;
}
