package com.eldorado.eldoradomock_vendorgateway.service;

import com.eldorado.eldoradomock_vendorgateway.repositories.entity.PricingDbDTO;
import com.eldorado.eldoradomock_vendorgateway.repositories.repository.PricingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class PricingCacheServiceTest {

  private static final String XAU = "XAU";
  private final PricingRepository pricingRepository = Mockito.mock(PricingRepository.class);
  private final PricingCacheService pricingCacheService =
      new PricingCacheService(pricingRepository);

  @Test
  void wholeClassTest() {
    given(pricingRepository.findAll())
        .willReturn(
            List.of(PricingDbDTO.builder().id(1).symbol(XAU).price(BigDecimal.TEN).build()));
    pricingCacheService.init();
    List<String> symbols = pricingCacheService.getSymbols();
    assertThat(symbols.size()).isOne();
    assertThat(symbols.get(0)).isEqualTo(XAU);
    Optional<BigDecimal> price = pricingCacheService.getPrice(XAU);
    assertThat(price).isPresent();
    assertThat(price.get()).isEqualTo(BigDecimal.TEN);
  }

  @Test
  void priceUpdateItemTwo() {
    given(pricingRepository.findAll())
            .willReturn(
                    List.of(
                            PricingDbDTO.builder().id(1).symbol(XAU).price(BigDecimal.TEN).build(),
                            PricingDbDTO.builder().id(1).symbol(XAU).price(BigDecimal.ONE).build()));
    pricingCacheService.init();
    pricingCacheService.updatePrices();
    List<String> symbols = pricingCacheService.getSymbols();
    assertThat(symbols.size()).isOne();
    assertThat(symbols.get(0)).isEqualTo(XAU);
    Optional<BigDecimal> price = pricingCacheService.getPrice(XAU);
    assertThat(price).isPresent();
    assertThat(price.get()).isEqualTo(BigDecimal.ONE);
  }

  @Test
  void priceUpdateBackToZero() {
    given(pricingRepository.findAll())
        .willReturn(
            List.of(
                PricingDbDTO.builder().id(1).symbol(XAU).price(BigDecimal.TEN).build(),
                PricingDbDTO.builder().id(1).symbol(XAU).price(BigDecimal.ONE).build()));
    pricingCacheService.init();
    pricingCacheService.updatePrices();
    pricingCacheService.updatePrices();
    List<String> symbols = pricingCacheService.getSymbols();
    assertThat(symbols.size()).isOne();
    assertThat(symbols.get(0)).isEqualTo(XAU);
    Optional<BigDecimal> price = pricingCacheService.getPrice(XAU);
    assertThat(price).isPresent();
    assertThat(price.get()).isEqualTo(BigDecimal.TEN);
  }

  @Test
  void getPriceOptionalEmpty() {
    Optional<BigDecimal> price = pricingCacheService.getPrice(XAU);
    assertThat(price).isEmpty();
  }
}
