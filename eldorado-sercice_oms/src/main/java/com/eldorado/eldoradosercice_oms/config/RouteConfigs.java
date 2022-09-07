package com.eldorado.eldoradosercice_oms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfigs {

  @ConfigurationProperties(prefix = "route-config.new-order")
  @Bean
  public RouteConfig newOrder() {
    return new RouteConfig();
  }

  @ConfigurationProperties(prefix = "route-config.child-order")
  @Bean
  public RouteConfig childOrderRoute() {
    return new RouteConfig();
  }

  @Data
  public static class RouteConfig {
    private String from;
    private String to;
    private String deadletter;
  }
}
