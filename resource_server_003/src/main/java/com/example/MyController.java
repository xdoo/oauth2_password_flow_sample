package com.example;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author straubec
 */
@RestController
public class MyController {
    
    @Secured("ROLE_RESOURCE_002_HELLO")
    @RequestMapping("/hello")
    public String hello(Authentication authentication) {   
        return "Hello " + authentication.getName() + "!\n";
    }
    
    @Secured("ROLE_RESOURCE_002_HELLOWORLD")
    @RequestMapping("/helloworld")
    public String helloWorld() {
        return "Hello World!";
    }
    
}
