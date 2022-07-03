package com.eldorado.eldoradoservice_pricing.models;

import com.eldorado.eldoradoservice_pricing.models.enums.MsgType;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class HeaderDTO {

    private MsgType msgType;
    private String senderCompId;
    private String targetCompId;
    private String onBehalfOfCompId;
    private OffsetDateTime sendingTime;
}
