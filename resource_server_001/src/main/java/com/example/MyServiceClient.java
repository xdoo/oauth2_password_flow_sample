package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author straubec
 */
@Service
@EnableCircuitBreaker
public class MyServiceClient {
    
    private static final Logger LOG= Logger.getLogger(MyServiceClient.class.getName() );
    
    protected RestTemplate template;

    public MyServiceClient(RestTemplate template) {
        this.template = template;
    }
    
    @HystrixCommand(fallbackMethod = "defaultHello")
    public String sayHello(Hello hello) {
        // hack for demo
        ResponseEntity<String> response = this.template.getForEntity("http://localhost:8071/hello", String.class);
        return String.format("Answer for %s from %s: " + response.getBody(), hello.getName(), hello.getCity());
    }

    public String defaultHello(Hello hello, Throwable e) {
        LOG.log(Level.SEVERE, e.getMessage());
        return "Fallback Answer: Hello World!\n";
    }
}
