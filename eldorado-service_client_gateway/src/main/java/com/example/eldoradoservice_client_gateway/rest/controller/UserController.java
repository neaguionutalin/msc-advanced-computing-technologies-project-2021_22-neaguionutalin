package com.example.eldoradoservice_client_gateway.rest.controller;

import com.example.eldoradoservice_client_gateway.model.AuthorizationDTO;
import com.example.eldoradoservice_client_gateway.model.LoginDTO;
import com.example.eldoradoservice_client_gateway.model.UserDTO;
import com.example.eldoradoservice_client_gateway.repository.entity.User;
import com.example.eldoradoservice_client_gateway.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/user")
public class UserController {
  private final UserService userService;

  @PostMapping("/login")
  public AuthorizationDTO login(@RequestBody LoginDTO loginDTO) {
    return userService.login(loginDTO);
  }

  @PostMapping(
      value = "/register",
      produces = APPLICATION_JSON_VALUE,
      consumes = APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('BROKER')")
  public User addUser(@RequestBody UserDTO userDTO) {
    return userService.register(userDTO);
  }
}
