package io.nology.fullstackexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

  @Value("${test.base-url}")
  private String baseUrl;

  @Bean
  @Primary
  public TestRestTemplate restTemplate() {
    return new TestRestTemplate(new RestTemplateBuilder().rootUri(baseUrl));
  }
}
