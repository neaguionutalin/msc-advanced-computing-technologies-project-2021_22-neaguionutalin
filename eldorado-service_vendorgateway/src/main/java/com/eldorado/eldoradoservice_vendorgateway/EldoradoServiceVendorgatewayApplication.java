package com.eldorado.eldoradoservice_vendorgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EldoradoServiceVendorgatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(EldoradoServiceVendorgatewayApplication.class, args);
  }
}
