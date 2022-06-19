package com.eldorado.eldoradomock_vendorgateway.service;

import com.eldorado.eldoradomock_vendorgateway.repositories.entity.PricingDbDTO;
import com.eldorado.eldoradomock_vendorgateway.repositories.repository.PricingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class PricingCacheService {

  private final PricingRepository pricingRepository;
  private final Map<String, List<BigDecimal>> prices = new HashMap<>();
  private final Map<String, Map<Integer, BigDecimal>> currentPrices = new HashMap<>();

  /**
   * get all prices available in the db and add them to a final object to be later used to construct
   * the price availability by symbol
   */
  @PostConstruct
  public void init() {
    List<PricingDbDTO> pricingDbDTOS = pricingRepository.findAll();
    for (String symbol : pricingDbDTOS.stream().map(PricingDbDTO::getSymbol).distinct().toList()) {
      prices.put(
          symbol,
          pricingDbDTOS.stream()
              .filter(t -> t.getSymbol().equals(symbol))
              .map(PricingDbDTO::getPrice)
              .toList());
    }
    updatePrices();
  }

  /**
   * every 5 seconds the price is being updated and when we get to the end of the array of prices we
   * start from the beginning. This ensures that the prices don't change too much between enquiries.
   */
  @Scheduled(cron = "*/5 * * * * ?")
  public void updatePrices() {
    for (String symbol : prices.keySet()) {
      Map<Integer, BigDecimal> currentPrice = currentPrices.get(symbol);
      if (currentPrice == null) {
        currentPrice = new HashMap<>();
        currentPrice.put(0, prices.get(symbol).get(0));
      } else {
        Integer pos = new ArrayList<>(currentPrice.keySet()).get(0);
        if (pos + 1 == prices.get(symbol).size()) {
          pos = 0;
        } else {
          pos++;
        }
        currentPrice = new HashMap<>();
        currentPrice.put(pos, prices.get(symbol).get(pos));
      }
      currentPrices.put(symbol, currentPrice);
    }
  }

  /**
   * @param symbol - the commodity we want to enquire
   * @return the price of the commodity or empty
   */
  public Optional<BigDecimal> getPrice(String symbol) {
    Map<Integer, BigDecimal> currentPrice = currentPrices.get(symbol);
    if (currentPrice == null) {
      return Optional.empty();
    }
    return Optional.of(currentPrice.get(new ArrayList<>(currentPrice.keySet()).get(0)));
  }

  public List<String> getSymbols() {
    return new ArrayList<>(prices.keySet());
  }
}
