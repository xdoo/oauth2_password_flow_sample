package com.example.sensor;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author straubec
 */
@Service
public class AppSensorClient implements SensorClient {

    private static final Logger LOG= Logger.getLogger(AppSensorClient.class.getName() );
    
    @Override
    public void detect() {
        LOG.log(Level.INFO, "detected something...");
    }
    
}
