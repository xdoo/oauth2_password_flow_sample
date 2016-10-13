package com.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author straubec
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyControllerTests {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Autowired
    private MyController controller;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer server;

    @Before
    public void setup() {
        this.server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @WithMockUser(username = "Hans", authorities = {"RESOURCE_001_HELLO"})
    public void testSayHello() {
        this.server.expect(requestTo("http://localhost:8071/hello")).andRespond(withSuccess("Hello Hans!", MediaType.TEXT_PLAIN));
        String greeting = this.controller.sayHello();
        assertThat(greeting, startsWith("Answer: Hello Hans!"));
    }

    @Test
    public void testSayHelloWithoutSecurity() {
        this.exception.expect(AuthenticationCredentialsNotFoundException.class);
        this.controller.sayHello();
    }

    @Test
    @WithMockUser(username = "Hans", authorities = {"DUMMY"})
    public void testSayHelloWithWrongRole() {
        this.exception.expect(AccessDeniedException.class);
        this.controller.sayHello();
    }

}
