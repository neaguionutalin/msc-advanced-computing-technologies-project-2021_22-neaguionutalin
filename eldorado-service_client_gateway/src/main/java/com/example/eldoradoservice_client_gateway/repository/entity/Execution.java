package com.example.eldoradoservice_client_gateway.repository.entity;

import com.example.eldoradoservice_client_gateway.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

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
  private OrdStatus ordStatus;
  private OrdType ordType;
  private Side side;
  private String symbol;
  private String text;
  private OffsetDateTime transactTime;
  private ExecType execType;
  private BigDecimal leavesQty;
  private Product product;
  private String senderCompId;
  private String targetCompId;
  private OffsetDateTime createdOn;
  private OffsetDateTime modifiedOn;
}
