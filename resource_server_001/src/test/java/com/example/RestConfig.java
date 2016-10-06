
package com.example;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author straubec
 */
@TestConfiguration
public class RestConfig {
    
    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplate();
    }
    
}

