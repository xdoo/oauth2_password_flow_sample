package com.example;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author straubec
 */
@Configuration
@Profile("test")
public class RestConfig {
    
   @Bean
   public RestTemplateBuilder restTemplateBuilder() {
       return new RestTemplateBuilder();
   }
    
}
