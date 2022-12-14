package com.eldorado.eldoradoservice_pricing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RouteConfigs {

    @Bean
    @ConfigurationProperties(prefix = "route-config-pricing.pricing")
    public RouteConfig priceImport(){
        return new RouteConfig();
    }

    @Data
    public static class RouteConfig {
        private String from;
        private String to;
        private String deadletter;
    }
}
