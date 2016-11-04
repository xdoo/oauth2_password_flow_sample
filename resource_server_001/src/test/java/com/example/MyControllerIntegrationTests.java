package com.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.test.OAuth2ContextConfiguration;
import org.springframework.security.oauth2.client.test.OAuth2ContextSetup;
import org.springframework.security.oauth2.client.test.RestTemplateHolder;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author straubec
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@OAuth2ContextConfiguration()
public class MyControllerIntegrationTests implements RestTemplateHolder {
    
    // external rest template to call the service from
    // outside (rest call with oauth)
    RestOperations restOperations = new RestTemplate();
    
    // interal rest template to call the mock server from
    // the service client
    @Autowired
    RestTemplate restTemplate;
    
    @Value("http://localhost:${local.server.port}")
    protected String host;
    
    @Rule
    public OAuth2ContextSetup context = OAuth2ContextSetup.standard(this);
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    private MockRestServiceServer server;

    @Before
    public void setup() {
        this.server = MockRestServiceServer.createServer(restTemplate);
    }
    
    @Test
    @OAuth2ContextConfiguration(User2Details.class)
    public void testHello() {
        this.server.expect(requestTo("http://localhost:8071/hello")).andRespond(withSuccess("Hello user02!", MediaType.TEXT_PLAIN));
        ResponseEntity<String> response = this.restOperations.postForEntity(host + "/hello", new Hello("Peter", "Munich"), String.class);
        assertThat(response.getBody(), startsWith("Answer for Peter from Munich: Hello user02!"));
    }
    
    @Test
    @OAuth2ContextConfiguration(User2Details.class)
    public void testWrongMethod() {
        this.exception.expect(HttpClientErrorException.class);
        this.restOperations.put(host + "/hello", new Hello("Peter", "Munich"));
    }
    
    @Test
    @OAuth2ContextConfiguration(User2Details.class)
    public void testBeanValidation() {
        this.exception.expect(HttpClientErrorException.class);
        this.restOperations.postForEntity(host + "/hello", new Hello("Peter", "München"), String.class);
    }
    
    @Test
    @OAuth2ContextConfiguration(User1Details.class)
    public void testHelloWithWrongRole() {
        this.exception.expect(UserDeniedAuthorizationException.class);
        this.restOperations.postForEntity(host + "/hello", new Hello("Peter", "Munich"), String.class);
    }
    
    @Test
    @OAuth2ContextConfiguration(User1Details.class) // make OAuth context happy
    public void testHelloWithoutSecurity() {
        RestTemplate tmpl = new RestTemplate(); // use rest template with no credentials
        this.exception.expect(HttpClientErrorException.class);
        tmpl.postForEntity(host + "/hello", new Hello("Peter", "Munich"), String.class);
    }
    

    @Override
    public void setRestTemplate(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public RestOperations getRestTemplate() {
        return this.restOperations;
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
