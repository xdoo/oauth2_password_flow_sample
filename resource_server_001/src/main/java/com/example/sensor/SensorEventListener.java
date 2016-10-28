package com.example.sensor;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 *
 * @author straubec
 */
@Component
public class SensorEventListener {
    
    private static final Logger LOG = Logger.getLogger(SensorEventListener.class.getName());

    @EventListener
    public void handleServletRequestEvent(ServletRequestHandledEvent event) {
        String method = event.getMethod();
        String requestUrl = event.getRequestUrl();
        String clientAddress = event.getClientAddress();
        String userName = event.getUserName();

        LOG.log(Level.INFO, String.format("requested '%s' using '%s' from '%s' with user '%s'.", requestUrl, method, clientAddress, userName));
    }

    @EventListener
    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        String userName = event.getAuthentication().getName();
        String userIp = this.getUserIp(event.getAuthentication());
        
        LOG.log(Level.INFO, String.format("user '%s' from '%s' logged into service.", userName, userIp));
    }

    @EventListener
    public void handleAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {

    }
    
    @EventListener
    public void handleContextRefreshedEvent(ContextRefreshedEvent event) {
        String applicationName = event.getApplicationContext().getApplicationName();
        
        LOG.log(Level.INFO, String.format("refreshed application context '%s'", applicationName));
    }

    private String getUserIp(Authentication authentication) {
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
            return null;
        }

        // retrieve IP address for failure
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();

        if (remoteAddress == null) {
            return null;
        }

        return remoteAddress;
    }

}
