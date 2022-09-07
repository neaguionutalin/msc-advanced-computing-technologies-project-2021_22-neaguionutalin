package com.eldorado.eldoradosercice_oms.exceptions;

import com.eldorado.eldoradosercice_oms.model.NewOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public abstract class OmsException extends RuntimeException {

  private ErrorCode errorCode;
  private NewOrder newOrder;

  protected OmsException(Throwable throwable) {
    super(throwable);
  }
}
