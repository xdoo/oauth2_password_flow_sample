package com.example.sensor;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 * Dummy implementation of appSensor.
 * 
 * @author straubec
 */
@Service
public class AppSensorClient implements SensorClient {

    private static final Logger LOG= Logger.getLogger(AppSensorClient.class.getName() );
    
    @Override
    public void detect(SensorEvent event) {
        LOG.log(Level.INFO, String.format("sensor event '%s' (%s) | system: %s (%s) | user: %s (%s)",
                event.getDetectionPointLabel(),
                event.getDetectionPointCategory(),
                event.getDetectionSystemName(),
                event.getDetectionSystemIp(),
                event.getUserName(),
                event.getUserIp()
                ));
    }
    
}
