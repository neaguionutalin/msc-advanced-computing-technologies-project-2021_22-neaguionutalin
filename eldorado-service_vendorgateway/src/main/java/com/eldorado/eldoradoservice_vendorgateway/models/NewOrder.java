package com.eldorado.eldoradoservice_vendorgateway.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewOrder {
  private HeaderDTO header;
  private NewOrderBody body;
  private Metadata metadata;
}
