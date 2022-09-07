package com.eldorado.eldoradosercice_oms.model;

import com.eldorado.eldoradosercice_oms.model.enums.OrdType;
import com.eldorado.eldoradosercice_oms.model.enums.Product;
import com.eldorado.eldoradosercice_oms.model.enums.Side;
import com.eldorado.eldoradosercice_oms.model.enums.TimeInForce;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderBody {
  private String symbol;
  private BigDecimal orderQty;
  private Side side;
  private String currency;
  private Product product;
  private OrdType ordType;
  private String clOrdId;
  private TimeInForce timeInForce;
  private OffsetDateTime transactTime;
}
