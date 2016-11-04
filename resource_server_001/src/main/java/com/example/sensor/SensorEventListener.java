package com.example.sensor;

import java.util.logging.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * This class creates {@link SensorEvent}s from spring {@link ApplicationEvent}s.
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

    /**
     * Sends an {@link SensorEvent}, if someone has been authenticated successfully.
     * 
     * @param event 
     */
    @EventListener
    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        SensorEvent sensorEvent = this.sensorEventFactory.createSensorEvent("Authentication",
                "SUCCESS",
                event.getAuthentication().getName(),
                this.getUserIp(event.getAuthentication()));
        this.sensor.detect(sensorEvent);
    }

    /**
     * Sends an {@link SensorEvent}, if an authentication failed. 
     * 
     * @param event 
     */
    @EventListener
    public void handleAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {
        SensorEvent sensorEvent = this.sensorEventFactory.createSensorEvent("Authentication",
                "FAILURE",
                event.getException().getMessage(),
                this.getUserIp(event.getAuthentication()));
        this.sensor.detect(sensorEvent);
    }

    /**
     * Extract the IP address from {@link Authentication}.
     * 
     * @param authentication
     * @return 
     */
    private String getUserIp(Authentication authentication) {
        if (authentication.getDetails() instanceof OAuth2AuthenticationDetails) {

            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            String remoteAddress = details.getRemoteAddress();

            if (remoteAddress == null) {
                return null;
            }

            return remoteAddress;
        }
        return null;
    }

}
