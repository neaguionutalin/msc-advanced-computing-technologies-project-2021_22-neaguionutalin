package com.eldorado.eldoradoservice_pricing.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MsgType {
    NEW_ORDER_SINGLE("D"),
    EXECUTION_REPORT("8"),
    MARKET_DATA("W");

    private final String code;
}
