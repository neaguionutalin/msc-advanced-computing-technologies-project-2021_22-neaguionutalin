package com.example.eldoradoservice_client_gateway.repository.repo;

import com.example.eldoradoservice_client_gateway.repository.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InstitutionsRepository extends JpaRepository<Institution, UUID> {

    Optional<Institution> findByFriendlyCompId(String compId);
}
