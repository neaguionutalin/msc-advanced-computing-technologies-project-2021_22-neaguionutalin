package com.eldorado.eldoradosercice_oms.utils;

import com.eldorado.eldoradosercice_oms.exceptions.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

  private static final ObjectMapper OBJECT_MAPPER = ObjectMapperBuilder.getObjectMapper();
  private final ProducerTemplate producerTemplate;

  public <T> void publish(String endpointUriToPublish, T message) {
    String body;
    try {
      body = OBJECT_MAPPER.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      log.error("Error during object mapping to json by ObjectMapper", e);
      throw new JsonMappingException(e);
    }
    producerTemplate.sendBody(endpointUriToPublish, body);
  }
}
