package com.example;

import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

/**
 *
 * @author straubec
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    
    private static final Logger LOG= Logger.getLogger( AuthorizationServerConfiguration.class.getName() );
    
        
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }
    
    /**
     * Configure OAuth2 to store clients inside a database.
     * 
     * @param clients
     * @throws Exception 
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .jdbc(dataSource)
                // this is the default user (admin)
                .withClient("acme")
                .secret("acmesecret")
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("USER")
                .scopes("read", "write")
                ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // inject this to enable password grants
                // see http://projects.spring.io/spring-security-oauth/docs/oauth2.html
                .authenticationManager(this.authenticationManager);
    }
    
}
