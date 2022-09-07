package com.eldorado.eldoradosercice_oms.service;

import com.eldorado.eldoradosercice_oms.config.RouteConfigs.RouteConfig;
import com.eldorado.eldoradosercice_oms.model.NewOrder;
import com.eldorado.eldoradosercice_oms.utils.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderPublisherService {

  private final EventPublisher eventPublisher;
  private final RouteConfig childOrderRoute;

  public void publishOrders(List<NewOrder> childOrders) {
    for (NewOrder childOrder : childOrders) {
      eventPublisher.publish(
          childOrderRoute
              .getTo()
              .replace("FRIENDLY_COMP_ID", childOrder.getHeader().getTargetCompId()),
          childOrder);
    }
  }
}
