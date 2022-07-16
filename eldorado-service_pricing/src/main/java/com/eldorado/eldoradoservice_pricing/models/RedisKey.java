package com.eldorado.eldoradoservice_pricing.models;

import com.eldorado.eldoradoservice_pricing.models.marketdata.MarketDataEntryDTO;
import com.eldorado.eldoradoservice_pricing.utils.DateFormat;
import lombok.Data;

@Data
public class RedisKey {

    private static final String KEY_SEPARATOR = "#";
    private static final String MARKETDATA = "marketdata";

    private final String bidKey;
    private final String offerKey;
    private final String allVendorKeys;

    public RedisKey(HeaderDTO headerDTO, String symbol) {
        this.bidKey = MARKETDATA +
                KEY_SEPARATOR +
                symbol +
                KEY_SEPARATOR +
                headerDTO.getSenderCompId() +
                KEY_SEPARATOR +
                "bid";
        this.offerKey = MARKETDATA +
                KEY_SEPARATOR +
                symbol +
                KEY_SEPARATOR +
                headerDTO.getSenderCompId() +
                KEY_SEPARATOR +
                "offer";
        this.allVendorKeys = MARKETDATA +
                KEY_SEPARATOR +
                symbol +
                KEY_SEPARATOR +
                headerDTO.getSenderCompId() +
                KEY_SEPARATOR + "*";
    }

    public String getValue (MarketDataEntryDTO marketDataEntryDTO, String compId){
        return marketDataEntryDTO.getMdEntrySize() +
                KEY_SEPARATOR +
                marketDataEntryDTO.getMdEntryPx() +
                KEY_SEPARATOR +
                marketDataEntryDTO.getQuoteEntryId() +
                KEY_SEPARATOR +
                marketDataEntryDTO.getQuoteCondition() +
                KEY_SEPARATOR +
                DateFormat.format(marketDataEntryDTO.getMdDateTime()) +
                KEY_SEPARATOR +
                compId;
    }
}
