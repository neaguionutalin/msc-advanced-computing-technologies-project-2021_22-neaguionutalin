package com.eldorado.activemq;


import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.replace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;


@RequiredArgsConstructor
public class ConsumerTemplateListener<T> implements Runnable {

  private final static String CONSUMER_TOPIC = "queue:Consumer.%s.VirtualTopic";
  protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
      .registerModule(new JavaTimeModule())
      .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS.WRITE_DATES_AS_TIMESTAMPS,
          SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

  private final ConsumerTemplate consumerTemplate;
  private final String activeMQQueueEndpoint;
  private final Class<T> className;
  private boolean pollMessage = true;

  @Getter
  private List<UnMarshalledMessage<T>> messages = new ArrayList<>();

  @Override
  public void run() {
    while (pollMessage) {
      process(consumerTemplate.receiveNoWait(activeMQQueueEndpoint));
    }
  }

  private void process(Exchange exchange) {
    if (exchange != null) {
      Message message = exchange.getMessage();
      String body = message.getBody(String.class);
      try {
        T t = OBJECT_MAPPER.readValue(body.getBytes(), className);
        messages.add(new UnMarshalledMessage<>(t, exchange));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }
  }

  public void stopListener() {
    pollMessage = false;
  }

  public void startListener() {
    Thread listenerThread;
    listenerThread = new Thread(this, "Listener_for_" + activeMQQueueEndpoint);
    listenerThread.setDaemon(true);
    listenerThread.start();
  }

  @Data
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class UnMarshalledMessage<T> {

    private final T unMarshalledMessage;
    private final Exchange exchange;
  }


  /*
     camelActiveMQVirtualTopicComponent is of format activemq:topic:Consumer.%randomString%.VirtualTopic.%topic_name%'
      The format is the same as in the application yaml file in the 'to' section of the route config, which is the producer topic name/config.
   */
  public static <T> ConsumerTemplateListener<T> getConsumerForCamelActiveMQVirtualTopic(
      ConsumerTemplate consumerTemplate, String camelActiveMQVirtualTopicComponent,
      Class<T> messageClassName) {
    String activeMQQueueComponentName = getConsumerQueueNameFromProducerVirtualTopic(
        camelActiveMQVirtualTopicComponent, false);
    return new ConsumerTemplateListener<>(consumerTemplate, activeMQQueueComponentName,
        messageClassName);
  }


  /*  Take an input of type 'activemq:topic:VirtualTopic.vendor.marketdata?jmsMessageType=Text&testConnectionOnStartup=true'
      and if stripComponentName is true returns (useful for JMS/ActiveMQ API)
        'Consumer.%randomString%.VirtualTopic.vendor.marketdata
       else (useful for camel components to listen to messages)
         'activemq:queue:Consumer.%randomString%.VirtualTopic.vendor.marketdata?jmsMessageType=Text&testConnectionOnStartup=true'
   */
  public static String getConsumerQueueNameFromProducerVirtualTopic(
      String producerTopicName, boolean stripComponentName) {
    String virtualTopic = replace(producerTopicName, "topic:VirtualTopic",
        format(CONSUMER_TOPIC, RandomStringUtils.randomAlphanumeric(8)));
    if (stripComponentName) {
      virtualTopic = StringUtils.stripStart(virtualTopic, "activemq:queue:");
      virtualTopic = StringUtils.substringBefore(virtualTopic, "?");
    }
    return virtualTopic;
  }

  /*
      converts: 'activemq:queue:Consumer.${spring.application.name}.VirtualTopic.oms.orders.${kitco.comp-id}?jmsMessageType=Text&testConnectionOnStartup=true'
      to: 'activemq:topic:VirtualTopic.oms.orders.${kitco.comp-id}?jmsMessageType=Text&testConnectionOnStartup=true'
   */
  public static String getProducerTopicNameFromConsumerQueueName(
      String consumerQueueName) {
    String topicName = StringUtils.substringAfter(consumerQueueName, ".VirtualTopic");
    return "activemq:topic:VirtualTopic".concat(topicName);
  }
}

