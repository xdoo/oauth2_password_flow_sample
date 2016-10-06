package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * OAuth Server for integration tests.
 * 
 * @author straubec
 */
@Configuration
@EnableAuthorizationServer
@SuppressWarnings("static-method")
public class AuthServer extends AuthorizationServerConfigurerAdapter {
    
    @Autowired AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
                clients
                .inMemory()

                .withClient("acme")
                .secret("acmesecret")
                .authorizedGrantTypes("password")
                .scopes("read", "write");
    }
    
}

@Configuration
@EnableWebSecurity
class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter{
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user01").password("password").roles("USER").and()
                .withUser("user02").password("password").roles("USER","RESOURCE_001_HELLO", "RESOURCE_002_HELLO", "RESOURCE_002_HELLOWORLD").and()
                .withUser("admin").password("admin").roles("USER", "ADMIN", "RESOURCE_001_HELLO", "RESOURCE_002_HELLOWORLD");
    }
    
}
