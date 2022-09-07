package com.eldorado.eldoradosercice_oms.exceptions;

import com.eldorado.eldoradosercice_oms.model.NewOrder;
import lombok.Getter;

import java.math.BigDecimal;

import static com.eldorado.eldoradosercice_oms.exceptions.ErrorCode.NOT_ENOUGH_POSITION_AVAILABLE;

@Getter
public class NotEnoughPositionAvailable extends OmsException {

  private final BigDecimal availablePosition;

  public NotEnoughPositionAvailable(BigDecimal availablePosition, NewOrder newOrder) {
    this.availablePosition = availablePosition;
    setNewOrder(newOrder);
    setErrorCode(NOT_ENOUGH_POSITION_AVAILABLE);
  }
}
