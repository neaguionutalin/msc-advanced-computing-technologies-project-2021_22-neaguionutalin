package com.example.eldoradoservice_client_gateway.service;

import com.example.eldoradoservice_client_gateway.exceptions.UserAlreadyExistsException;
import com.example.eldoradoservice_client_gateway.model.AuthorizationDTO;
import com.example.eldoradoservice_client_gateway.model.LoginDTO;
import com.example.eldoradoservice_client_gateway.model.UserDTO;
import com.example.eldoradoservice_client_gateway.repository.entity.User;
import com.example.eldoradoservice_client_gateway.repository.repo.RolesRepository;
import com.example.eldoradoservice_client_gateway.repository.repo.UsersRepository;
import com.example.eldoradoservice_client_gateway.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UsersRepository usersRepository;
  private final RolesRepository rolesRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  public User register(UserDTO userDTO) {
    Optional<User> userOptional = usersRepository.findByEmail(userDTO.getEmail());
    if (userOptional.isPresent()) throw new UserAlreadyExistsException(userDTO.getEmail());
    String salt = BCrypt.gensalt();
    User user =
        User.builder()
            .id(UUID.randomUUID())
            .email(userDTO.getEmail())
            .fullName(userDTO.getFullName())
            .password(BCrypt.hashpw(userDTO.getPassword(), salt))
            .salt(salt)
            .roles(
                rolesRepository.findAllByNameIn(
                    userDTO.getRoles() != null ? userDTO.getRoles() : List.of()))
            .createdOn(OffsetDateTime.now())
            .modifiedOn(OffsetDateTime.now())
            .build();
    return usersRepository.saveAndFlush(user);
  }

  public AuthorizationDTO login(LoginDTO loginDTO) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    return AuthorizationDTO.builder().authorization(jwt).build();
  }
}
