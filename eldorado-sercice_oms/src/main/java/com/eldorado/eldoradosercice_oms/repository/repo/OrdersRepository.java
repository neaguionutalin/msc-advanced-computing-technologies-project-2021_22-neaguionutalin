package com.eldorado.eldoradosercice_oms.repository.repo;

import com.eldorado.eldoradosercice_oms.repository.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByClOrdIdAndTargetCompId(String clOrdId, String targetCompId);
}
