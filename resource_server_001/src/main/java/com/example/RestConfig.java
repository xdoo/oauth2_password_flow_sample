package com.example;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

/**
 *
 * @author straubec
 */
@Configuration
public class RestConfig {
    
    @Bean
    public OAuth2RestTemplate restTemplate(UserInfoRestTemplateFactory factory) {
        return factory.getUserInfoRestTemplate();
    }
    
}
