package com.eldorado.eldoradosercice_oms.exceptions;

import com.eldorado.eldoradosercice_oms.model.ExecutionReport;

import static com.eldorado.eldoradosercice_oms.exceptions.ErrorCode.ORDER_NOT_FOUND;

public class OrderNotFoundException extends OmsException {

  public OrderNotFoundException(ExecutionReport executionReport) {
    setErrorCode(ORDER_NOT_FOUND);
    setExecutionReport(executionReport);
  }
}
