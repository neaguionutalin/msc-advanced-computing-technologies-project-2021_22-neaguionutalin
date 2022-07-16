package com.example.eldoradoservice_market_data.rest.controller;

import com.example.eldoradoservice_market_data.models.RedisMarketData;
import com.example.eldoradoservice_market_data.models.enums.EntryType;
import com.example.eldoradoservice_market_data.models.enums.MarketDataType;
import com.example.eldoradoservice_market_data.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/marketData")
public class MarketDataController {

    private final MarketDataService marketDataService;

    @GetMapping(path = "/{marketDataType}/{symbol}")
    public RedisMarketData getMarketData(@PathVariable MarketDataType marketDataType,
                                         @PathVariable String symbol,
                                         @RequestParam(required = false) Optional<EntryType> entryType) {
        return marketDataService.getMarketData(marketDataType, symbol, entryType);
    }
}
