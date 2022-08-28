package com.example.eldoradoservice_client_gateway.repository.repo;

import com.example.eldoradoservice_client_gateway.repository.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RolesRepository extends JpaRepository<Role, UUID> {
  List<Role> findAllByNameIn(List<String> roleNames);
}
