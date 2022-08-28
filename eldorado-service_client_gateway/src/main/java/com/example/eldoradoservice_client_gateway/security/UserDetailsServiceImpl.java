package com.example.eldoradoservice_client_gateway.security;

import com.example.eldoradoservice_client_gateway.exceptions.UserNotRegisteredException;
import com.example.eldoradoservice_client_gateway.repository.entity.User;
import com.example.eldoradoservice_client_gateway.repository.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> userOptional = usersRepository.findByEmail(email);
    if (userOptional.isEmpty()) throw new UserNotRegisteredException(email);
    return userOptional.get();
  }
}
