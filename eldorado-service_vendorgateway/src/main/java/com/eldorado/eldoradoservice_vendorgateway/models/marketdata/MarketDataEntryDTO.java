package com.eldorado.eldoradoservice_vendorgateway.models.marketdata;

import com.eldorado.eldoradoservice_vendorgateway.models.enums.MdEntryType;
import com.eldorado.eldoradoservice_vendorgateway.models.enums.QuoteCondition;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MarketDataEntryDTO {

    private MdEntryType mdEntryType;
    private BigDecimal mdEntryPx;
    private String currency;
    private BigDecimal mdEntrySize;
    private QuoteCondition quoteCondition;
    private UUID quoteEntryId;
    private Integer mdEntryPositionNo;
    private OffsetDateTime mdDateTime;
}
