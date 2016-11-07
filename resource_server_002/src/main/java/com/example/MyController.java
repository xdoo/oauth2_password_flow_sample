package com.example;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author straubec
 */
@RestController
public class MyController {
    
    private MyService service;

    public MyController(MyService service) {
        this.service = service;
    }
    
    @PreAuthorize("hasAuthority('RESOURCE_002_HELLO')")
    @RequestMapping("/hello")
    public String hello(Authentication authentication) {   
        return service.hello(authentication);
    }
    
    @PreAuthorize("hasAuthority('RESOURCE_002_HELLOWORLD')")
    @RequestMapping("/helloworld")
    public String helloWorld() {
        return "Hello World!";
    }
    
}
