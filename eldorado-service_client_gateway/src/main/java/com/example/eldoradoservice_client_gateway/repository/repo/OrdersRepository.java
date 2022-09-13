package com.example.eldoradoservice_client_gateway.repository.repo;

import com.example.eldoradoservice_client_gateway.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Order, UUID> {
  Optional<Order> findByClOrdIdAndSenderCompId(String clOrdId, String senderCompId);
}
