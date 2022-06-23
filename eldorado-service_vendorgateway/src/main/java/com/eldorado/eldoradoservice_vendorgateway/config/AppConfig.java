package com.eldorado.eldoradoservice_vendorgateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@Data
@ConfigurationProperties(prefix = "vendor")
public class AppConfig {
    private String uri;
    private String marketData;
    private String vendorCompId;
    private String platformCompId;
    private BigDecimal commission;
    private String currency;
}
