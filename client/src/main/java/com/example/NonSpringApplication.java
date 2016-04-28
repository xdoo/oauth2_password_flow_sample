package com.example;

import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author straubec
 */
public class NonSpringApplication {
    
    private static final Logger LOG= Logger.getLogger( Application.class.getName() );
    
    public static void main(String[] args) throws Exception{
        NonSpringApplication app = new NonSpringApplication();
        app.run();
    }
    
    private void run(){
        String credentials = "acme:acmesecret";
        String base64encoded = Base64Encoder.encode (credentials);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:9999/uaa/oauth/token");
        Response response = target
                .queryParam("grant_type", "password")
                .queryParam("username", "user")
                .queryParam("password", "password")
                .request()
                .header(HttpHeaders.AUTHORIZATION, "Basic " + base64encoded)
                .get();
        LOG.info("result >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + response.toString());
        
    }
    
}
