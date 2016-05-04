package com.example;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * You simply can test this with curl:
 * $ curl acme:acmesecret@localhost:9999/uaa/oauth/token -d grant_type=password -d username=user -d password=password
 * $ {"access_token":"232f6fe0-f117-4a0f-8dd6-9c7a7b5f1cd2","token_type":"bearer","refresh_token":"f9e87b15-f764-47b7-a34a-9665cd4d4967","expires_in":43199,"scope":"openid"}
 * $ TOKEN=232f6fe0-f117-4a0f-8dd6-9c7a7b5f1cd2
 * $ curl -H "Authorization: Bearer $TOKEN" localhost:9999/uaa/user
 * $ {"details":{"remoteAddress":"127.0.0.1","sessionId":null,"tokenValue":"232f6fe0-f117-4a0f-8dd6-9c7a7b5f1cd2","tokenType":"Bearer",.... 
 * 
 * @author straubec
 */
@SpringBootApplication
@EnableAuthorizationServer
@RestController
@Configuration
@EnableResourceServer
public class Application extends AuthorizationServerConfigurerAdapter {

    private static final Logger LOG= Logger.getLogger( Application.class.getName() );
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
    
    
    

    /**
     * User info endpoint. 
     * 
     * @param user
     * @return 
     */
    @RequestMapping("/user")
    public Principal user(Principal user) {
        LOG.log(Level.INFO, "reading user with name > {0}", user.getName());
        return user;
    }
    
    /**
     * Endpoint to add oauth2 client.
     * 
     * @param clientDetails 
     */
    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public void addClient(@RequestBody ClientDetails clientDetails) {
        LOG.log(Level.INFO, "add client with id > {0}", clientDetails.getClientId());
        this.jdbcClientDetailsService.addClientDetails(clientDetails);
    }
    
    /**
     * Endpoint to remove oauth2 client.
     * 
     * @param clientId 
     */
    @RequestMapping(value = "/client", method = RequestMethod.DELETE)
    public void removeClient(@RequestParam("clientId") String clientId) {
        LOG.log(Level.INFO, "remove client with id > {0}", clientId);
        this.jdbcClientDetailsService.removeClientDetails(clientId);
    }
    
    /**
     * Endpoint to get a new oauth2 client detail template.
     * 
     * @return 
     */
    @RequestMapping(value = "/client", method = RequestMethod.GET)
    public ClientDetails getEmptyClientDetails() {
        return new BaseClientDetails();
    }
}
