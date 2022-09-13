package com.example.eldoradoservice_client_gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfigs {

  @Bean
  @ConfigurationProperties(prefix = "route-config.new-order")
  public RouteConfig newOrderRoute() {
    return new RouteConfig();
  }

  @Data
  public static class RouteConfig {
    private String from;
    private String to;
    private String deadletter;
  }
}
