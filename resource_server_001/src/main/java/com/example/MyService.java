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
public class MyService {
    
    private static final Logger LOG= Logger.getLogger( MyService.class.getName() );
    
    protected RestTemplate template;

    public MyService(RestTemplate template) {
        this.template = template;
    }
    
    @HystrixCommand(fallbackMethod = "defaultHello")
    public String sayHello() {
        // hack for demo
        ResponseEntity<String> response = this.template.getForEntity("http://localhost:8071/hello", String.class);
        return "Answer: " + response.getBody();
    }

    public String defaultHello(Throwable e) {
        LOG.log(Level.SEVERE, e.getMessage());
        return "Fallback Answer: Hello World!\n";
    }
}
