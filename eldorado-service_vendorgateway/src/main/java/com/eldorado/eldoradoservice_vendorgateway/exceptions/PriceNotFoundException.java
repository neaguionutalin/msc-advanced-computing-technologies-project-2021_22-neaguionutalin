package com.eldorado.eldoradoservice_vendorgateway.exceptions;

import com.eldorado.eldoradoservice_vendorgateway.models.NewOrder;
import lombok.Getter;

@Getter
public class PriceNotFoundException extends VendorGatewayException {

  private final NewOrder newOrder;

  public PriceNotFoundException(NewOrder newOrder) {
    this.newOrder = newOrder;
  }
}
