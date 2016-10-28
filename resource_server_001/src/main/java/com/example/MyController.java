package com.example;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.sensor.RE8;

/**
 *
 * @author straubec
 */
@RestController
public class MyController {

    private final MyServiceClient service;

    public MyController(MyServiceClient service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('RESOURCE_001_HELLO')")
    @RequestMapping("/hello")
    @RE8
    public String sayHello() {
        return this.service.sayHello();
    }

}
