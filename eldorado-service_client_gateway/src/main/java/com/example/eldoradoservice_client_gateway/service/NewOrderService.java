package com.example.eldoradoservice_client_gateway.service;

import com.example.eldoradoservice_client_gateway.config.RouteConfigs.RouteConfig;
import com.example.eldoradoservice_client_gateway.exceptions.OrderAlreadyExistsException;
import com.example.eldoradoservice_client_gateway.model.HeaderDTO;
import com.example.eldoradoservice_client_gateway.model.enums.MsgType;
import com.example.eldoradoservice_client_gateway.model.orders.Metadata;
import com.example.eldoradoservice_client_gateway.model.orders.NewOrder;
import com.example.eldoradoservice_client_gateway.model.orders.NewOrderBody;
import com.example.eldoradoservice_client_gateway.repository.entity.Order;
import com.example.eldoradoservice_client_gateway.repository.entity.User;
import com.example.eldoradoservice_client_gateway.repository.repo.OrdersRepository;
import com.example.eldoradoservice_client_gateway.utils.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.example.eldoradoservice_client_gateway.model.enums.OrdStatus.PENDING_NEW;

@Service
@RequiredArgsConstructor
public class NewOrderService {

  private final OrdersRepository ordersRepository;
  private final EventPublisher eventPublisher;
  private final RouteConfig newOrderRoute;

  @Value("${cg.comp-id}")
  private String platformCompId;

  public void createNewOrder(NewOrderBody newOrderBody) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        (UsernamePasswordAuthenticationToken)
            SecurityContextHolder.getContext().getAuthentication();
    Optional<Order> optionalOrder =
        ordersRepository.findByClOrdIdAndSenderCompId(
            newOrderBody.getClOrdId(),
            ((User) usernamePasswordAuthenticationToken.getPrincipal())
                .getInstitution()
                .getFriendlyCompId());
    if (optionalOrder.isPresent()) throw new OrderAlreadyExistsException(newOrderBody.getClOrdId());
    NewOrder newOrder =
        NewOrder.builder()
            .header(
                HeaderDTO.builder()
                    .msgType(MsgType.NEW_ORDER_SINGLE)
                    .senderCompId(
                        ((User) usernamePasswordAuthenticationToken.getPrincipal())
                            .getInstitution()
                            .getFriendlyCompId())
                    .targetCompId(platformCompId)
                    .sendingTime(OffsetDateTime.now())
                    .build())
            .body(newOrderBody)
            .metadata(Metadata.builder().orderId(UUID.randomUUID()).build())
            .build();
    Order order =
        Order.builder()
            .id(UUID.randomUUID())
            .orderId(newOrder.getMetadata().getOrderId())
            .symbol(newOrder.getBody().getSymbol())
            .orderQty(newOrder.getBody().getOrderQty())
            .side(newOrder.getBody().getSide())
            .currency(newOrder.getBody().getCurrency())
            .product(newOrder.getBody().getProduct())
            .ordType(newOrder.getBody().getOrdType())
            .clOrdId(newOrder.getBody().getClOrdId())
            .timeInForce(newOrder.getBody().getTimeInForce())
            .transactTime(newOrder.getBody().getTransactTime())
            .leavesQty(newOrder.getBody().getOrderQty())
            .ordStatus(PENDING_NEW)
            .senderCompId(newOrder.getHeader().getSenderCompId())
            .targetCompId(newOrder.getHeader().getTargetCompId())
            .createdOn(OffsetDateTime.now())
            .modifiedOn(OffsetDateTime.now())
            .build();
    ordersRepository.saveAndFlush(order);
    eventPublisher.publish(newOrderRoute.getTo(), newOrder);
  }
}
