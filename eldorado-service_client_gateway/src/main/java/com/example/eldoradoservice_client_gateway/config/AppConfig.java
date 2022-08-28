package com.example.eldoradoservice_client_gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "client-config")
public class AppConfig {

    private String hmacSecret;
}
