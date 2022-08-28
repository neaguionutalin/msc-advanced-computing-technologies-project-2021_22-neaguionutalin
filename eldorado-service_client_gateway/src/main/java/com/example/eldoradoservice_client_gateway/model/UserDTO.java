package com.example.eldoradoservice_client_gateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
  private String fullName;
  private String email;
  private String password;
  private List<String> roles;
}
