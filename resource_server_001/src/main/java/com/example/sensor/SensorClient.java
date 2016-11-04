package com.example.sensor;

/**
 * This sensor client gets security sensor events and pushes them to the
 * central sensor service (e.g. appSensor).
 * 
 * @author straubec
 */
public interface SensorClient {
    
    /**
     * detect sensor event
     * 
     * @param event 
     */
    public void detect(SensorEvent event);
    
}
