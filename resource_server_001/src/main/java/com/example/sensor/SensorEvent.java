package com.example.sensor;

import java.util.List;
import org.springframework.validation.ObjectError;

/**
 * The sensor event with some base information. Most of them should match to
 * the appSensor event structure.
 * 
 * @author straubec
 */
public class SensorEvent {
    
    private long timestamp;
    private String detectionSystemIp;
    private String detectionSystemName;
    private String detectionPointCategory;
    private String detectionPointLabel;
    private String userName;
    private String userIp;
    private String resourceMethod;
    private List<ObjectError> errors;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDetectionSystemIp() {
        return detectionSystemIp;
    }

    public void setDetectionSystemIp(String detectionSystemIp) {
        this.detectionSystemIp = detectionSystemIp;
    }

    public String getDetectionSystemName() {
        return detectionSystemName;
    }

    public void setDetectionSystemName(String detectionSystemName) {
        this.detectionSystemName = detectionSystemName;
    }

    public String getDetectionPointCategory() {
        return detectionPointCategory;
    }

    public void setDetectionPointCategory(String detectionPointCategory) {
        this.detectionPointCategory = detectionPointCategory;
    }

    public String getDetectionPointLabel() {
        return detectionPointLabel;
    }

    public void setDetectionPointLabel(String detectionPointLabel) {
        this.detectionPointLabel = detectionPointLabel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getResourceMethod() {
        return resourceMethod;
    }

    public void setResourceMethod(String resourceMethod) {
        this.resourceMethod = resourceMethod;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }
    
}
