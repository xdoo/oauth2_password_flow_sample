package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableResourceServer
@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAspectJAutoProxy
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
