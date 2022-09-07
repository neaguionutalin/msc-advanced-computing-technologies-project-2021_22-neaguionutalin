package com.eldorado.eldoradoservice_vendorgateway.models;

import com.eldorado.eldoradoservice_vendorgateway.models.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecutionReportBody {
  private BigDecimal avgPx;
  private String clOrdId;
  private UUID orderId;
  private BigDecimal cumQty;
  private String currency;
  private UUID execId;
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
}
