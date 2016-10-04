package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.junit.rules.ExpectedException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Integration tests for {@link MyController} 
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
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    /**
     * test 'hello' with privileged user
     */
    @Test
    @OAuth2ContextConfiguration(User2Details.class)
    public void testHello() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(host + "/hello", String.class);
        assertThat(response.getBody(), startsWith("Hello user02!"));
    }
    
    /**
     * security test: 'hello' with unprivileged user
     */
    @Test
    @OAuth2ContextConfiguration(User1Details.class)
    public void testHelloWithWrongRole() {
        this.exception.expect(UserDeniedAuthorizationException.class);
        this.restTemplate.getForEntity(host + "/hello", String.class);
    }
    
    /**
     * security test: 'hello' with no security
     */
    @Test
    @OAuth2ContextConfiguration(User1Details.class) // make OAuth context happy
    public void testHelloWithoutSecurity() {
        RestTemplate tmpl = new RestTemplate(); // use rest template with no credentials
        this.exception.expect(HttpClientErrorException.class);
        tmpl.getForEntity(host + "/hello", String.class);
    }
    

    /**
     * test 'helloworld' with privileged user
     */
    @Test
    @OAuth2ContextConfiguration(User2Details.class)
    public void testHelloworld() {
        ResponseEntity<String> response = this.restTemplate.getForEntity(host + "/helloworld", String.class);
        assertThat(response.getBody(), startsWith("Hello World!"));
    }
    
    /**
     * security test: 'helloworld' with unprivileged user
     */
    @Test
    @OAuth2ContextConfiguration(User1Details.class)
    public void testHelloworldWithWrongRole() {
        this.exception.expect(UserDeniedAuthorizationException.class);
        this.restTemplate.getForEntity(host + "/helloworld", String.class);
    }
    
    /**
     * security test: 'helloworld' with no security
     */
    @Test
    @OAuth2ContextConfiguration(User1Details.class) // make OAuth context happy
    public void testHelloWorldWithoutSecurity() {
        RestTemplate tmpl = new RestTemplate(); // use rest template with no credentials
        this.exception.expect(HttpClientErrorException.class);
        tmpl.getForEntity(host + "/helloworld", String.class);
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
 * can access nothing
 * 
 * @author straubec
 */
class User1Details extends ResourceOwnerPasswordResourceDetails {
    public User1Details(final Object obj) {
        MyControllerIntegrationTests it = (MyControllerIntegrationTests) obj;
        setAccessTokenUri(it.getHost() + "/oauth/token");
        setClientId("acme");
        setClientSecret("acmesecret");
        setUsername("user01");
        setPassword("password");
        setGrantType("password");
    }
}


/**
 * can access 'hello and 'helloworld'
 * 
 * @author straubec
 */
class User2Details extends ResourceOwnerPasswordResourceDetails {
    public User2Details(final Object obj) {
        MyControllerIntegrationTests it = (MyControllerIntegrationTests) obj;
        setAccessTokenUri(it.getHost() + "/oauth/token");
        setClientId("acme");
        setClientSecret("acmesecret");
        setGrantType("password");
        setUsername("user02");
        setPassword("password");
    }
}
