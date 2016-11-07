/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 *
 * @author straubec
 */
@Service
public class MyService {

    public String hello(Authentication authentication) {
        return "Hello " + authentication.getName() + "!\n";
    }

}
