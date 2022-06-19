package com.eldorado.eldoradomock_vendorgateway.rest.controller;

import com.eldorado.eldoradomock_vendorgateway.models.PricingDTO;
import com.eldorado.eldoradomock_vendorgateway.service.PricingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class PricingControllerTest {
  private final PricingService pricingService = Mockito.mock(PricingService.class);

  private final PricingController pricingController = new PricingController(pricingService);

  @Test
  void getPrices() {
    List<PricingDTO> pricingDTOS =
        List.of(
            PricingDTO.builder()
                .symbol("XAU")
                .price(BigDecimal.ONE)
                .quantity(BigDecimal.TEN)
                .build());
    given(pricingService.getPrices(any())).willReturn(pricingDTOS);
    List<PricingDTO> actual = pricingController.getPrices(Optional.of("XAU"));
    assertThat(actual.size()).isOne();
    assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(pricingDTOS.get(0));
  }
}
