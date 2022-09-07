package com.eldorado.eldoradosercice_oms.model;

import com.eldorado.eldoradosercice_oms.model.enums.MsgType;
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
