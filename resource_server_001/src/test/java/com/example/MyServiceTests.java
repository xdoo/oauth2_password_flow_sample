package com.example;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author straubec
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class MyServiceTests {
    
    @Autowired
    private MyService service;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private MockRestServiceServer server;
    
    @Before
    public void setup() {
        this.server = MockRestServiceServer.createServer(restTemplate);
    }
    
    @Test
    public void testSayHello() {
        this.server.expect(requestTo("http://localhost:8071/hello")).andRespond(withSuccess("Hello Hans!", MediaType.TEXT_PLAIN));
        String greeting = this.service.sayHello();
        assertThat(greeting, startsWith("Answer: Hello Hans!"));
    }
    
}

