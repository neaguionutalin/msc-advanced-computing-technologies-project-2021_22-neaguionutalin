package com.eldorado.eldoradosercice_oms.repository.entities;

import com.eldorado.eldoradosercice_oms.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

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
  private BigDecimal cumQty;
  private BigDecimal lastPx;
  private BigDecimal lastQty;
  private BigDecimal avgPx;
  private OrdStatus ordStatus;
  private String senderCompId;
  private String targetCompId;
  private UUID parentOrderId;
  private OffsetDateTime createdOn;
  private OffsetDateTime modifiedOn;
}
