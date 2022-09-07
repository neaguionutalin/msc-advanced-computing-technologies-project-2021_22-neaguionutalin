package com.eldorado.eldoradoservice_vendorgateway.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TimeInForce {
    IMMEDIATE_OR_CANCEL("3");

    @JsonValue private final String code;
}
