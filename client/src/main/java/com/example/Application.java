package com.example;

import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableOAuth2Client
public class Application {

    private static final Logger LOG= Logger.getLogger( Application.class.getName() );
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public OAuth2ProtectedResourceDetails oauth2ProtectedResourceDetails() {
        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();

        // you can get this from a resource file as well
        details.setClientId("acme");
        details.setClientSecret("acmesecret");
        details.setAccessTokenUri("http://localhost:9999/uaa/oauth/token");
        
        // set username password (if you've multible users, you 
        // must do that per session)
        details.setUsername("user");
        details.setPassword("password");
        
        return details;
    }
}
