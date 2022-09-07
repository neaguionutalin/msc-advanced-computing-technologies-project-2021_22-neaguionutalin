package com.eldorado.eldoradosercice_oms.service;

import com.eldorado.eldoradosercice_oms.config.AppConfig;
import com.eldorado.eldoradosercice_oms.model.*;
import com.eldorado.eldoradosercice_oms.model.enums.MsgType;
import com.eldorado.eldoradosercice_oms.model.enums.Side;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderSplitService {
  private final AppConfig appConfig;

  public List<NewOrder> createNewOrders(NewOrder newOrder, RedisMarketData redisMarketData) {
    List<NewOrder> newOrderList = new ArrayList<>();
    BigDecimal remainingQuantity = newOrder.getBody().getOrderQty();
    List<BidAndOffer> bidAndOfferList;
    if (newOrder.getBody().getSide() == Side.BUY) bidAndOfferList = redisMarketData.getOffers();
    else bidAndOfferList = redisMarketData.getBids();
    for (int i = 0;
        i < bidAndOfferList.size() && remainingQuantity.compareTo(BigDecimal.ZERO) > 0;
        i++) {
      BigDecimal ordQty;
      if (newOrder.getBody().getOrderQty().compareTo(remainingQuantity) <= 0)
        ordQty = newOrder.getBody().getOrderQty();
      else ordQty = remainingQuantity;
      NewOrder currentNewOrder =
          NewOrder.builder()
              .header(
                  HeaderDTO.builder()
                      .msgType(MsgType.NEW_ORDER_SINGLE)
                      .sendingTime(OffsetDateTime.now())
                      .senderCompId(appConfig.getPlatformCompId())
                      .targetCompId(bidAndOfferList.get(i).getFriendlyCompId())
                      .build())
              .body(
                  NewOrderBody.builder()
                      .symbol(newOrder.getBody().getSymbol())
                      .orderQty(ordQty)
                      .side(newOrder.getBody().getSide())
                      .currency(newOrder.getBody().getCurrency())
                      .product(newOrder.getBody().getProduct())
                      .ordType(newOrder.getBody().getOrdType())
                      .clOrdId(String.format("%s-%s", newOrder.getMetadata().getOrderId(), i))
                      .timeInForce(newOrder.getBody().getTimeInForce())
                      .transactTime(OffsetDateTime.now())
                      .build())
              .build();
      newOrderList.add(currentNewOrder);
    }
    return newOrderList;
  }
}
