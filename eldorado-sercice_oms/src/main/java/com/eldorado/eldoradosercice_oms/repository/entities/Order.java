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

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
  @Id private UUID id;
  private UUID orderId;
  private String symbol;
  private BigDecimal orderQty;

  @Enumerated(STRING)
  private Side side;

  private String currency;

  @Enumerated(STRING)
  private Product product;

  @Enumerated(STRING)
  private OrdType ordType;

  private String clOrdId;

  @Enumerated(STRING)
  private TimeInForce timeInForce;

  private OffsetDateTime transactTime;
  private BigDecimal leavesQty;
  private OrdStatus ordStatus;
  private OffsetDateTime createdOn;
  private OffsetDateTime modifiedOn;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "parent_order_id")
  private List<Order> childOrders;
}
