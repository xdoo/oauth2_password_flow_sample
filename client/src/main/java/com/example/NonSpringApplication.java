package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

/**
 *
 * @author straubec
 */
public class NonSpringApplication {

    private static final Logger LOG = Logger.getLogger(NonSpringApplication.class.getName());
    
    private Executor executor;
    private final ObjectMapper mapper = new ObjectMapper();
    private OAuth2TokenBean oauth;

    public static void main(String[] args) throws Exception {
        NonSpringApplication app = new NonSpringApplication();
        app.run();
    }

    private void run() throws URISyntaxException, IOException {
        // set security context
        this.createExecutor();
        
        // aquire first token 
        this.aquireToken();
        LOG.log(Level.INFO, "aquired new OAuth token > {0}", this.oauth.getAccess_token());
        
        // call a protected resource
        this.requestProtectedResource();
        
        // aquire a fresh token
        this.refreshToken();
        LOG.log(Level.INFO, "aquired fresh OAuth token > {0}", this.oauth.getAccess_token());
        
        // call protected resource with new token again
        this.requestProtectedResource();
    }
    
    /**
     * Call the OAuth2 protected 'hello' ressource.
     * 
     * @param uri 
     */
    private void requestProtectedResource() throws IOException {
        
        String result = Request
                .Get("http://localhost:8070/hello")
                .addHeader("Authorization", String.format(" Bearer %s", this.oauth.getAccess_token()))
                .execute()
                .returnContent()
                .asString();
        LOG.log(Level.INFO, "result from oauth protected resource > {0}", result);
    }
    
    
    /**
     * Create the Basic Authentication context.
     */
    private void createExecutor() {
        this.executor = Executor.newInstance()
                .auth(new HttpHost("localhost", 9999, "http"), new UsernamePasswordCredentials("acme", "acmesecret"))
                .authPreemptive(new HttpHost("localhost", 9999, "http"));
    }
    
    /**
     * Aquire token from OAuth2 Server.
     * 
     * @return
     * @throws URISyntaxException
     * @throws IOException 
     */
    private void aquireToken() throws URISyntaxException, IOException {
        // build uri with parameters (should be dynamic)
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(9999)
                .setPath("/uaa/oauth/token")
                .setParameter("grant_type", "password")
                .setParameter("username", "user")
                .setParameter("password", "password")
                .build();
        
        InputStream result = this.executor.execute(Request.Post(uri)).returnContent().asStream();
        this.oauth = this.mapper.readValue(result, OAuth2TokenBean.class);
    }
    
    private void refreshToken() throws URISyntaxException, IOException {
        // build uri with parameters (should be dynamic)
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(9999)
                .setPath("/uaa/oauth/token")
                .setParameter("grant_type", "refresh_token")
                .setParameter("refresh_token", this.oauth.getRefresh_token())
                .build();
        
        InputStream result = this.executor.execute(Request.Post(uri)).returnContent().asStream();
        this.oauth = this.mapper.readValue(result, OAuth2TokenBean.class);
    }

}
