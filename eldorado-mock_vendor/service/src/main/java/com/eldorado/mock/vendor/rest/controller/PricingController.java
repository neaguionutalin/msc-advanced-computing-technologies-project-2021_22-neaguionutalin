package com.eldorado.mock.vendor.rest.controller;

import com.eldorado.mock.vendor.models.PricingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PricingController {

  @GetMapping(path = "/api/v1/mock/pricing", produces = APPLICATION_JSON_VALUE)
  public List<PricingDTO> getPrices(
      @RequestParam(value = "symbol", required = false) Optional<String> symbol) {

    return Collections.emptyList();
  }
}
