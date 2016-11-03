package com.example;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author straubec
 */
@RestController
public class MyController {
    
    private final MyServiceClient service;
    
    private static final Logger LOG= Logger.getLogger(MyController.class.getName() );

    public MyController(MyServiceClient service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('RESOURCE_001_HELLO')")
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String sayHello(@Valid @RequestBody Hello hello) {
        
//        if(result.hasErrors()) {
//            LOG.info("Errors in Bean!");
//        }
        
        return this.service.sayHello(hello);
    } 

}
