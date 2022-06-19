package com.eldorado.eldoradomock_vendorgateway.service;

import com.eldorado.eldoradomock_vendorgateway.models.PricingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PricingService {

  private final PricingCacheService pricingCacheService;

  /**
   * @param symbol optional query parameter to query by symbol
   * @return a list of all pricing objects
   */
  public List<PricingDTO> getPrices(Optional<String> symbol) {
    List<String> symbols = pricingCacheService.getSymbols();
    if (symbol.isPresent()) {
      symbols = symbols.stream().filter(t -> t.equals(symbol.get())).toList();
    }
    return symbols.stream()
        .map(
            t -> {
              if (pricingCacheService.getPrice(t).isEmpty()
                  || pricingCacheService.getPrice(t).get().compareTo(BigDecimal.ZERO) == 0) {
                return PricingDTO.builder().symbol(t).price(null).quantity(null).build();
              }
              return PricingDTO.builder()
                  .symbol(t)
                  .price(pricingCacheService.getPrice(t).get())
                  .quantity(BigDecimal.TEN)
                  .build();
            })
        .toList();
  }
}
