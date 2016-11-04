package com.example.sensor;

import java.util.logging.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author straubec
 */
@Component
public class SensorEventListener {
    
    private static final Logger LOG = Logger.getLogger(SensorEventListener.class.getName());
    
    private final SensorClient sensor;
    private final SensorEventFactory sensorEventFactory;

    public SensorEventListener(SensorClient sensor, SensorEventFactory sensorEventFactory) {
        this.sensor = sensor;
        this.sensorEventFactory = sensorEventFactory;
    }

    @EventListener
    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        SensorEvent sensorEvent = this.sensorEventFactory.createSensorEvent("AuthenticationException", 
                "AE3",
                event.getAuthentication().getName(), 
                this.getUserIp(event.getAuthentication()));
        this.sensor.detect(sensorEvent);
    }

    @EventListener
    public void handleAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {
        SensorEvent sensorEvent = this.sensorEventFactory.createSensorEvent("AuthenticationException", 
                "AE2",
                event.getAuthentication().getName(), 
                this.getUserIp(event.getAuthentication()));
        this.sensor.detect(sensorEvent);
    }

    private String getUserIp(Authentication authentication) {
        if (authentication.getDetails() instanceof WebAuthenticationDetails) {

        // retrieve IP address for failure
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();

        if (remoteAddress == null) {
            return null;
        }

        return remoteAddress;
        }
        return null;
    }

}
