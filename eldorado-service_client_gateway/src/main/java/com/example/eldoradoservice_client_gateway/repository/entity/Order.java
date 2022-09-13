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
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Order {
  @Id private UUID id;
  private UUID orderId;
  private String symbol;
  private BigDecimal orderQty;
  private Side side;
  private String currency;
  private Product product;
  private OrdType ordType;
  private String clOrdId;
  private TimeInForce timeInForce;
  private OffsetDateTime transactTime;
  private BigDecimal leavesQty;
  private OrdStatus ordStatus;
  private String senderCompId;
  private String targetCompId;
  private OffsetDateTime createdOn;
  private OffsetDateTime modifiedOn;
}
