package com.eldorado.eldoradoservice_pricing.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RouteConfigs {

  @Bean
  @ConfigurationProperties(prefix = "route-configs.pricing")
  public RouteConfig pricingRoute() {
    return new RouteConfig();
  }

  @Data
  public static class RouteConfig {
    private String from;
  }
}
