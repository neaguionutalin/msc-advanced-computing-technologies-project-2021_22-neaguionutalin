package com.eldorado.eldoradoservice_pricing.services;

import com.eldorado.eldoradoservice_pricing.models.RedisKey;
import com.eldorado.eldoradoservice_pricing.models.enums.MdEntryType;
import com.eldorado.eldoradoservice_pricing.models.marketdata.MarketDataDTO;
import com.eldorado.eldoradoservice_pricing.models.marketdata.MarketDataEntryDTO;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PricingService {

  private final StringRedisTemplate stringRedisTemplate;

  @Value("${pricing.expiry}")
  private Integer expiry;

  @Handler
  public void handle(@Body MarketDataDTO marketDataDTO) {
    RedisKey redisKey =
        new RedisKey(marketDataDTO.getHeader(), marketDataDTO.getBody().getSymbol());
    Set<String> vendorKeys = stringRedisTemplate.keys(redisKey.getAllVendorKeys());
    if (vendorKeys != null) stringRedisTemplate.delete(vendorKeys);
    Set<TypedTuple<String>> bids = new HashSet<>();
    Set<TypedTuple<String>> offers = new HashSet<>();
    for (MarketDataEntryDTO entry : marketDataDTO.getBody().getNoMDEntries()) {
      if (entry.getMdEntryType() == MdEntryType.BID) {
        bids.add(
            new DefaultTypedTuple<>(
                redisKey.getValue(entry, marketDataDTO.getHeader().getSenderCompId()),
                entry.getMdEntryPx().doubleValue()));
      } else if (entry.getMdEntryType() == MdEntryType.OFFER) {
        offers.add(
            new DefaultTypedTuple<>(
                redisKey.getValue(entry, marketDataDTO.getHeader().getSenderCompId()),
                entry.getMdEntryPx().doubleValue()));
      }
    }
    if (!bids.isEmpty()) {
      stringRedisTemplate.opsForZSet().add(redisKey.getBidKey(), bids);
      stringRedisTemplate.expire(redisKey.getBidKey(), Duration.ofMillis(expiry));
    }
    if (!offers.isEmpty()) {
      stringRedisTemplate.opsForZSet().add(redisKey.getOfferKey(), offers);
      stringRedisTemplate.expire(redisKey.getOfferKey(), Duration.ofMillis(expiry));
    }
  }
}
