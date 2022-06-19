package com.eldorado.mock.vendor;

import co.elastic.apm.attach.ElasticApmAttacher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.opentracing.starter.CamelOpenTracing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
@CamelOpenTracing
public class Application {

  /*
     Replace package com.eldorado.changetoservicename to use the right service name
   */
  public static void main(String[] args) {
    ElasticApmAttacher.attach();
    SpringApplication.run(Application.class, args);
  }

}
