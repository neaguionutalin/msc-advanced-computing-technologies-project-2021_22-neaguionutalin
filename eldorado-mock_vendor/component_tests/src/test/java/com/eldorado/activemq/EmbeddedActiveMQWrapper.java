package com.eldorado.activemq;

import org.apache.activemq.broker.BrokerService;

public class EmbeddedActiveMQWrapper {

  private static String PORT = "61617";

  private static BrokerService brokerService = null;

  public static synchronized void startBroker() throws Exception {
    if (brokerService == null) {
      brokerService = new BrokerService();
      brokerService.addConnector(String.format("tcp://localhost:%s", PORT));
      brokerService.setPersistent(false);
      brokerService.start();
    }
  }
}
