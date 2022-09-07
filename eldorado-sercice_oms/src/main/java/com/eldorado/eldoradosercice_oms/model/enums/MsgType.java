package com.eldorado.eldoradosercice_oms.model.enums;

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
