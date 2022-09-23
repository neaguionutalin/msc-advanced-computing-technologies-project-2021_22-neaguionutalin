package com.eldorado.eldoradosercice_oms.service;

import com.eldorado.eldoradosercice_oms.exceptions.NotEnoughPositionAvailable;
import com.eldorado.eldoradosercice_oms.model.NewOrder;
import com.eldorado.eldoradosercice_oms.repository.repo.ExecutionReportsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.eldorado.eldoradosercice_oms.model.enums.Side.BUY;

@Service
@RequiredArgsConstructor
public class PositionAvailabilityService {

  private final ExecutionReportsRepository executionReportsRepository;

  public void checkAvailablePosition(NewOrder newOrder) {
    if (newOrder.getBody().getSide() == BUY) return;
    BigDecimal availableValue =
        executionReportsRepository.getClientPosition(
            newOrder.getHeader().getSenderCompId(), newOrder.getBody().getSymbol());
    if (availableValue.compareTo(newOrder.getBody().getOrderQty()) < 0)
      throw new NotEnoughPositionAvailable(availableValue, newOrder);
  }
}
