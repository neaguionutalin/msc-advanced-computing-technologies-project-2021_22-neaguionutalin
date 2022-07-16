package com.example.eldoradoservice_market_data.models;

import lombok.Data;

@Data
public class RedisKey {

  private static final String KEY_SEPARATOR = "#";
  private static final String MARKETDATA = "marketdata";

  private final String bidKey;
  private final String offerKey;
  private final String allVendorKeys;

  public RedisKey(String symbol) {
    this.bidKey = MARKETDATA + KEY_SEPARATOR + symbol + KEY_SEPARATOR + "*" + KEY_SEPARATOR + "bid";
    this.offerKey =
        MARKETDATA + KEY_SEPARATOR + symbol + KEY_SEPARATOR + "*" + KEY_SEPARATOR + "offer";
    this.allVendorKeys =
        MARKETDATA + KEY_SEPARATOR + symbol + KEY_SEPARATOR + "*" + KEY_SEPARATOR + "*";
  }
}
