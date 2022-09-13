package com.eldorado.eldoradosercice_oms.service;

import com.eldorado.eldoradosercice_oms.model.NewOrder;
import com.eldorado.eldoradosercice_oms.repository.entities.Order;
import com.eldorado.eldoradosercice_oms.repository.repo.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderPersistenceService {
  private final NewOrderMapper newOrderMapper;
  private final OrdersRepository ordersRepository;

  public void saveOrders(List<NewOrder> newOrderList, NewOrder parentNewOrder) {
    Order parentOrder = newOrderMapper.mapToOrder(parentNewOrder, null);
    parentOrder = ordersRepository.saveAndFlush(parentOrder);
    Order finalParentOrder = parentOrder;
    List<Order> childOrders =
        newOrderList.stream().map(t -> newOrderMapper.mapToOrder(t, finalParentOrder.getId())).toList();
    ordersRepository.saveAllAndFlush(childOrders);
  }
}
