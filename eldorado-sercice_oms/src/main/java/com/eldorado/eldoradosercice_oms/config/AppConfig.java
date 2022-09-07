package com.eldorado.eldoradosercice_oms.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppConfig {

  private String platformCompId;
}
