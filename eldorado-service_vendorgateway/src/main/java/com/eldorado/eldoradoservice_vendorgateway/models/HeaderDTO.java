package com.eldorado.eldoradoservice_vendorgateway.models;

import com.eldorado.eldoradoservice_vendorgateway.models.enums.MsgType;
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
