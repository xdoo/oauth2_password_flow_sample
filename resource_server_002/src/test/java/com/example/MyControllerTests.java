package com.example;

import org.springframework.security.access.AccessDeniedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyControllerTests {
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Autowired
    private MyController controller;

    @Test
    @WithMockUser(username="hans",authorities = {"RESOURCE_002_HELLOWORLD"})
    public void testHelloWorld() {
        assertThat(this.controller.helloWorld(), startsWith("Hello World!"));
    }
    
    @Test
    public void testHelloWorldWithoutSecurity() {
        this.exception.expect(AuthenticationCredentialsNotFoundException.class);
        this.controller.helloWorld();
    }
    
    @Test
    @WithMockUser(username="hans",roles={"DUMMY"})
    public void testHelloWorldWithWrongRole() {
        this.exception.expect(AccessDeniedException.class);
        this.controller.helloWorld();
    }
    
    @Test
    @WithMockUser(username="hans",authorities={"RESOURCE_002_HELLO"})
    public void testHello() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(this.controller.hello(authentication), startsWith("Hello hans!"));
    }

}
