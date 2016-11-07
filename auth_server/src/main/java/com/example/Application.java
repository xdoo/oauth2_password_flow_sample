package com.example;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;

/**
 * OAuth2 Server
 * 
 * @author straubec
 */
@SpringBootApplication
public class Application extends AuthorizationServerConfigurerAdapter {

    private static final Logger LOG= Logger.getLogger( Application.class.getName() );
   
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
