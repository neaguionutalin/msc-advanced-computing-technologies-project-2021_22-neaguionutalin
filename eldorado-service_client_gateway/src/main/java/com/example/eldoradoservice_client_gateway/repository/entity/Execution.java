package com.example.eldoradoservice_client_gateway.repository.entity;

import com.example.eldoradoservice_client_gateway.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "execution_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Execution {
  @Id private UUID execId;
  private BigDecimal avgPx;
  private String clOrdId;
  private UUID orderId;
  private BigDecimal cumQty;
  private String currency;
  private BigDecimal lastPx;
  private BigDecimal lastQty;
  private BigDecimal orderQty;

  @Enumerated(STRING)
  private OrdStatus ordStatus;

  @Enumerated(STRING)
  private OrdType ordType;

  @Enumerated(STRING)
  private Side side;

  private String symbol;
  private String text;
  private OffsetDateTime transactTime;

  @Enumerated(STRING)
  private ExecType execType;

  private BigDecimal leavesQty;

  @Enumerated(STRING)
  private Product product;

  private String senderCompId;
  private String targetCompId;
  private OffsetDateTime createdOn;
  private OffsetDateTime modifiedOn;
}
