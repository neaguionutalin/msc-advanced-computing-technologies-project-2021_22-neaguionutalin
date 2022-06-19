package com.eldorado.eldoradoservice_vendorgateway.utils;

import com.eldorado.eldoradoservice_vendorgateway.exceptions.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import static com.eldorado.eldoradoservice_vendorgateway.utils.ObjectMapperUtil.getMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

  private static final String IDEMPOTENT_KEY_NAME = "idempotency_key";
  private static final ObjectMapper OBJECT_MAPPER = getMapper();
  private final ProducerTemplate producerTemplate;

  public <T> void publish(String endpointUriToPublish, T message, String idempotentKeyValue) {
    String body;
    try {
      body = OBJECT_MAPPER.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      log.error("Error during object mapping to json by ObjectMapper", e);
      throw new JsonMappingException(e);
    }
    producerTemplate.sendBodyAndHeader(
        endpointUriToPublish, body, IDEMPOTENT_KEY_NAME, idempotentKeyValue);
  }
}
