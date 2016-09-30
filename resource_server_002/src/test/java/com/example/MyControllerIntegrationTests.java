package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.test.OAuth2ContextConfiguration;
import org.springframework.security.oauth2.client.test.OAuth2ContextSetup;
import org.springframework.security.oauth2.client.test.RestTemplateHolder;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestOperations;
import static org.hamcrest.MatcherAssert.assertThat;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * http://stackoverflow.com/questions/29510759/how-to-test-spring-security-oauth2-resource-server-security
 * 
 * @author straubec
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@OAuth2ContextConfiguration()
public class MyControllerIntegrationTests implements RestTemplateHolder {
    
    RestOperations restTemplate = new RestTemplate();
    
    @Value("http://localhost:${local.server.port}")
    protected String host;
    
    
    @Rule
    public OAuth2ContextSetup context = OAuth2ContextSetup.standard(this);

    @Test
    @OAuth2ContextConfiguration(User2Details.class)
    public void testHelloworld() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(host + "/helloworld", String.class);
        assertThat(response.getBody(), startsWith("Hello World!"));
    }

    @Override
    public void setRestTemplate(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public RestOperations getRestTemplate() {
        return this.restTemplate;
    }

    public String getHost() {
        return host;
    }
    
    
}


/**
 * cannot access
 * 
 * @author straubec
 */
class User1Details extends ResourceOwnerPasswordResourceDetails {
    public User1Details(final Object obj) {
        MyControllerIntegrationTests it = (MyControllerIntegrationTests) obj;
        setAccessTokenUri(it.getHost() + "/oauth/token");
        setClientId("acme");
        setUsername("user01");
        setPassword("password");
    }
}


/**
 * can access
 * 
 * @author straubec
 */
class User2Details extends ResourceOwnerPasswordResourceDetails {
    public User2Details(final Object obj) {
        MyControllerIntegrationTests it = (MyControllerIntegrationTests) obj;
        setAccessTokenUri(it.getHost() + "/oauth/token");
        setClientId("acme");
        setUsername("user02");
        setPassword("password");
    }
}
