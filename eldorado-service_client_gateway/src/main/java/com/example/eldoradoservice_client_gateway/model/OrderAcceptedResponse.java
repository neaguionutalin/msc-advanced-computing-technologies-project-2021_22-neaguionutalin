package com.example.eldoradoservice_client_gateway.model;

import com.example.eldoradoservice_client_gateway.model.enums.NewOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderAcceptedResponse {
  private NewOrderStatus status;
}
