package io.nology.fullstackexample.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    String[] allowedOrigins = {
      "http://127.0.0.1:5173",
      "http://localhost:5173",
      "http://localhost:3000",
    };
    registry
      .addMapping("/**")
      .allowedOrigins(allowedOrigins)
      .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
      .allowedHeaders("*");
  }
}
