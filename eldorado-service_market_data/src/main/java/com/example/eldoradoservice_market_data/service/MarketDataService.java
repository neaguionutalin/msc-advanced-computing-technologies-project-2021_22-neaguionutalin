package com.example.eldoradoservice_market_data.service;

import com.example.eldoradoservice_market_data.models.RedisKey;
import com.example.eldoradoservice_market_data.models.RedisMarketData;
import com.example.eldoradoservice_market_data.models.enums.EntryType;
import com.example.eldoradoservice_market_data.models.enums.MarketDataType;
import com.example.eldoradoservice_market_data.utils.BidAndOfferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.eldoradoservice_market_data.models.enums.EntryType.BID;
import static com.example.eldoradoservice_market_data.models.enums.EntryType.OFFER;

@Service
@RequiredArgsConstructor
public class MarketDataService {

  private static final String UNION = "marketdata#union#u";
  private final StringRedisTemplate stringRedisTemplate;
  private final BidAndOfferMapper bidAndOfferMapper;

  public RedisMarketData getMarketData(
      MarketDataType marketDataType, String symbol, Optional<EntryType> entryType) {
    RedisKey redisKey = new RedisKey(symbol);
    Set<String> symbolKeysBids;
    Set<String> symbolKeysOffers;
    int end = marketDataType == MarketDataType.TOP ? 0 : -1;
    if (entryType.isEmpty()) {
      symbolKeysBids = stringRedisTemplate.keys(redisKey.getBidKey());
      symbolKeysOffers = stringRedisTemplate.keys(redisKey.getOfferKey());
    } else if (entryType.get() == BID) {
      symbolKeysBids = stringRedisTemplate.keys(redisKey.getBidKey());
      symbolKeysOffers = new HashSet<>();
    } else if (entryType.get() == OFFER) {
      symbolKeysBids = new HashSet<>();
      symbolKeysOffers = stringRedisTemplate.keys(redisKey.getOfferKey());
    } else {
      symbolKeysBids = new HashSet<>();
      symbolKeysOffers = new HashSet<>();
    }
    List<String> bidKeys = new ArrayList<>();
    if (symbolKeysBids != null) bidKeys = new ArrayList<>(symbolKeysBids);
    List<String> offerKeys = new ArrayList<>();
    if (symbolKeysOffers != null) offerKeys = new ArrayList<>(symbolKeysOffers);
    Set<TypedTuple<String>> bids;
    if (bidKeys.size() == 0) bids = new HashSet<>();
    else if (bidKeys.size() == 1)
      bids = stringRedisTemplate.opsForZSet().reverseRangeWithScores(bidKeys.get(0), 0, end);
    else {
      String uuid = UUID.randomUUID().toString();
      stringRedisTemplate
          .opsForZSet()
          .unionAndStore(bidKeys.get(0), bidKeys.subList(1, bidKeys.size()), UNION + uuid);
      bids = stringRedisTemplate.opsForZSet().reverseRangeWithScores(UNION + uuid, 0, end);
    }

    Set<TypedTuple<String>> offers;
    if (offerKeys.size() == 0) offers = new HashSet<>();
    else if (offerKeys.size() == 1)
      offers = stringRedisTemplate.opsForZSet().rangeWithScores(offerKeys.get(0), 0, end);
    else {
      String uuid = UUID.randomUUID().toString();
      stringRedisTemplate
          .opsForZSet()
          .unionAndStore(offerKeys.get(0), offerKeys.subList(1, offerKeys.size()), UNION + uuid);
      offers = stringRedisTemplate.opsForZSet().rangeWithScores(UNION + uuid, 0, end);
    }
    if (bids == null) {
      bids = new HashSet<>();
    }
    if (offers == null) {
      offers = new HashSet<>();
    }
    return RedisMarketData.builder()
        .type(marketDataType)
        .bids(
            bids.stream()
                .map(t -> bidAndOfferMapper.mapBidAndOffer(Objects.requireNonNull(t.getValue())))
                .toList())
        .offers(
            offers.stream()
                .map(t -> bidAndOfferMapper.mapBidAndOffer(Objects.requireNonNull(t.getValue())))
                .toList())
        .build();
  }
}
