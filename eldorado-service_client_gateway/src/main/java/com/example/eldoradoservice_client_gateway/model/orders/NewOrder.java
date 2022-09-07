package com.example.eldoradoservice_client_gateway.model.orders;

import com.example.eldoradoservice_client_gateway.model.HeaderDTO;
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
}
