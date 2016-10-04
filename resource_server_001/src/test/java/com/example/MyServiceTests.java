package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

/**
 *
 * @author straubec
 */
@RunWith(SpringRunner.class)
@RestClientTest(MyService.class)
public class MyServiceTests {
    
    @Autowired 
    private MyService service;
    
    @Autowired
    private MockRestServiceServer server;
    
    @Test
    public void testSayHello() {
        this.server.expect(requestTo("http://localhost:8071/hello")).andRespond(withSuccess("Hello Hans!", MediaType.TEXT_PLAIN));
        String greeting = this.service.sayHello();
        assertThat(greeting, startsWith("Answer: Hello Hans!"));
    }
    
}

@Configuration
class OAuthConfig {

    @Bean
    public OAuth2RestTemplate createOAuthRestTemplate() {
        return new OAuth2RestTemplate(new User1Details());
    }
    
}

class User1Details extends ResourceOwnerPasswordResourceDetails {
    public User1Details() {
//        MyControllerIntegrationTests it = (MyControllerIntegrationTests) obj;
//        setAccessTokenUri(it.getHost() + "/oauth/token");
        setClientId("acme");
        setClientSecret("acmesecret");
        setUsername("user01");
        setPassword("password");
        setGrantType("password");
    }
}
