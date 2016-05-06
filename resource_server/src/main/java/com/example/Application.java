package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.security.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableResourceServer
@RestController
@EnableCircuitBreaker
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/hello")
    public String home(Principal user) {
        return this.calculateHello(user);
    }
    
    @HystrixCommand(fallbackMethod = "defaultHello")
    private String calculateHello(Principal user) {
        return "Hello " + user.getName();
    }
    
    private String defaultHello(){
        return "Hello World!";
    }
    
}
