package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 *
 * @author straubec
 */
@Configuration
public class CustomResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        
           // only secure channel, https, is allowed
//           http.requiresChannel().anyRequest().requiresSecure();

           // static resources
           http.authorizeRequests().antMatchers("/uaa/h2-console").permitAll();

           http.authorizeRequests().anyRequest().authenticated();
        
//        http
//                .requestMatchers()
//                    .antMatchers("/uaa/client/**", "/uaa/user")
//                .and()
//                    .authorizeRequests().anyRequest().hasAuthority("USER_ADMIN")
//                ;
//        
//        http.csrf().disable();
    }

}
