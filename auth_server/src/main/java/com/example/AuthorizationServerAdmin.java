package com.example;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class provides endpoints to communicate with the OAuth2 Server. You 
 * can get user information. Or you can add and delete new clients.
 * 
 * 
 * @author straubec
 */
@EnableResourceServer
@RestController
public class AuthorizationServerAdmin extends ResourceServerConfigurerAdapter {
    
    private static final Logger LOG= Logger.getLogger(AuthorizationServerAdmin.class.getName() );
    
    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;    
    
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
     * Endpoint to add a new oauth2 client.
     * 
     * @param config
     */
    @RequestMapping(value = "/admin/client", method = RequestMethod.POST)
    public void addClient(@RequestBody ClientConfigBean config) {
        LOG.log(Level.INFO, "add client with id > {0}", config.getClientId());
        this.jdbcClientDetailsService.addClientDetails(this.createClientDetails(config));
    }
    
    /**
     * Endpoint to remove oauth2 client.
     * 
     * @param clientId 
     */
    @RequestMapping(value = "/admin/client", method = RequestMethod.DELETE)
    public void removeClient(@RequestParam("clientId") String clientId) {
        LOG.log(Level.INFO, "remove client with id > {0}", clientId);
        this.jdbcClientDetailsService.removeClientDetails(clientId);
    }
    
    /**
     * Endpoint to get a new oauth2 client detail template.
     * 
     * @return 
     */
    @RequestMapping(value = "/admin/client", method = RequestMethod.GET)
    public ClientConfigBean getEmptyClientDetails() {
        LOG.log(Level.INFO, "requesting new client configuration");
        ClientConfigBean config = new ClientConfigBean();
        config.setClientId("my-clientId");
        config.setClientSecret("my-clientSecret");
        
        return config;
    }
    
    /**
     * Create client configuration.
     * 
     * TODO use configuration values here.
     * 
     * @param config
     * @return 
     */
    private ClientDetails createClientDetails(ClientConfigBean config) {
        
        // fill client detail configuration
        BaseClientDetails detail = new BaseClientDetails();
        
        // credentials
        detail.setClientId(config.getClientId());
        detail.setClientSecret(config.getClientSecret());
        // token validity (one day)
        detail.setAccessTokenValiditySeconds(60 * 60 * 24);
        // grant types
        List<String> grantTypes = new ArrayList<>();
        grantTypes.add("password");
        grantTypes.add("refresh_token");
        detail.setAuthorizedGrantTypes(grantTypes);
        // scopes
        List<String> scopes = new ArrayList<>();
        scopes.add("read");
        scopes.add("write");
        detail.setScope(grantTypes);
        
        return detail;
    }
    
}
