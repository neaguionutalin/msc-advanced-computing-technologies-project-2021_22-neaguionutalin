package com.example.eldoradoservice_client_gateway.model;

import com.example.eldoradoservice_client_gateway.model.enums.MsgType;
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
  private OffsetDateTime sendingTime;
}
