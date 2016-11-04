package com.example.sensor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * This factory creates a pre configured sensor event.
 * 
 * @author straubec
 */
@Service
public class SensorEventFactory {
    
    /**
     * spring.application.name must be available in the application
     * properties file.
     */
    @Value("${spring.application.name}")
    private String name;
    
    private final String ip;

    /**
     * 
     * @throws UnknownHostException 
     */
    public SensorEventFactory() throws UnknownHostException {
        InetAddress IP=InetAddress.getLocalHost();
        this.ip = IP.getHostAddress();
    }
    
    /**
     * Creates a new instance of {@link SensorEvent}.
     * 
     * @param detectionPointCategory
     * @param detectionPointLabel
     * @return 
     */
    public SensorEvent createSensorEvent(String detectionPointCategory, String detectionPointLabel) {
        SensorEvent sensorEvent = new SensorEvent();
        
        // fill meta data
        sensorEvent.setTimestamp(System.currentTimeMillis());
        sensorEvent.setDetectionSystemName(this.name);
        sensorEvent.setDetectionSystemIp(this.ip);
        
        // detection point info
        sensorEvent.setDetectionPointCategory(detectionPointCategory);
        sensorEvent.setDetectionPointLabel(detectionPointLabel);
        
        return sensorEvent;
    }
    
    /**
     * Creates a new instance of {@link SensorEvent}.
     * 
     * @param detectionPointCategory
     * @param detectionPointLabel
     * @param userName
     * @return 
     */
    public SensorEvent createSensorEvent(String detectionPointCategory, String detectionPointLabel, String userName) {
        SensorEvent sensorEvent = this.createSensorEvent(detectionPointCategory, detectionPointLabel);
        sensorEvent.setUserName(userName);
        
        return sensorEvent;
    }

    /**
     * Creates a new instance of {@link SensorEvent}.
     * 
     * @param detectionPointCategory
     * @param detectionPointLabel
     * @param userName
     * @param userIp
     * @return 
     */
    public SensorEvent createSensorEvent(String detectionPointCategory, String detectionPointLabel, String userName, String userIp) {
        SensorEvent sensorEvent = this.createSensorEvent(detectionPointCategory, detectionPointLabel, userName);
        sensorEvent.setUserIp(userIp);
        
        return sensorEvent;
    }
    
}
