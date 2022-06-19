package com.eldorado.eldoradomock_vendorgateway.service;

import com.eldorado.eldoradomock_vendorgateway.models.PricingDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

class PricingServiceTest {
  private static final String XAU = "XAU";
  private final PricingCacheService pricingCacheService = Mockito.mock(PricingCacheService.class);
  private final PricingService pricingService = new PricingService(pricingCacheService);

  @Test
  void getPrices() {
    given(pricingCacheService.getSymbols()).willReturn(List.of(XAU));
    given(pricingCacheService.getPrice(eq(XAU))).willReturn(Optional.of(BigDecimal.TEN));
    List<PricingDTO> pricingDTOS =
        List.of(
            PricingDTO.builder()
                .symbol(XAU)
                .price(BigDecimal.TEN)
                .quantity(BigDecimal.TEN)
                .build());
    List<PricingDTO> actual = pricingService.getPrices(Optional.of(XAU));
    assertThat(actual.size()).isOne();
    assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(pricingDTOS.get(0));
  }

  @Test
  void getPricesNull() {
    given(pricingCacheService.getSymbols()).willReturn(List.of(XAU));
    given(pricingCacheService.getPrice(eq(XAU))).willReturn(Optional.empty());
    List<PricingDTO> pricingDTOS =
            List.of(
                    PricingDTO.builder()
                              .symbol(XAU)
                              .price(null)
                              .quantity(null)
                              .build());
    List<PricingDTO> actual = pricingService.getPrices(Optional.of(XAU));
    assertThat(actual.size()).isOne();
    assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(pricingDTOS.get(0));
  }
}
