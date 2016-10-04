package com.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

/**
 *
 * @author straubec
 */
@RunWith(SpringRunner.class)
@RestClientTest(MyService.class)
@ActiveProfiles("test")
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

