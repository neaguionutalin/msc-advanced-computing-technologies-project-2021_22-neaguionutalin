package com.eldorado;

import com.eldorado.activemq.EmbeddedActiveMQWrapper;
import com.eldorado.mock.vendor.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
@Slf4j
public abstract class BaseComponentTest {

  @BeforeClass
  public static void createBroker() throws Exception {
    EmbeddedActiveMQWrapper.startBroker();
  }
}
