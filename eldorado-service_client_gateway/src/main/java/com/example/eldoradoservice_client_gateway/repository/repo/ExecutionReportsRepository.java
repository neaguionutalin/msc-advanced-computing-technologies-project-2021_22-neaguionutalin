package com.example.eldoradoservice_client_gateway.repository.repo;

import com.example.eldoradoservice_client_gateway.repository.entity.Execution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExecutionReportsRepository extends JpaRepository<Execution, UUID> {

    List<Execution> findAllByClOrdIdAndTargetCompId(String clOrdId, String compId);
}
