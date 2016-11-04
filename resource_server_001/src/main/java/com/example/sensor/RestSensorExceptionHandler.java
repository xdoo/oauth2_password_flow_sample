package com.example.sensor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class generates {@link SensorEvent}s from Rest Exceptions.
 * @author straubec
 */
@ControllerAdvice
public class RestSensorExceptionHandler extends ResponseEntityExceptionHandler {
    
    private final SensorClient sensor;
    private final SensorEventFactory sensorEventFactory;

    public RestSensorExceptionHandler(SensorClient sensor, SensorEventFactory sensorEventFactory) {
        this.sensor = sensor;
        this.sensorEventFactory = sensorEventFactory;
    }
    
    /**
     * Rest method not supported.
     * 
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return 
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        SensorEvent sensorEvent = this.sensorEventFactory.createSensorEvent("Request","METHOD NOT SUPPORTED", request.getUserPrincipal().getName());
        this.sensor.detect(sensorEvent);
        return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
    }

    /**
     * Method argument not valid.
     * 
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return 
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        SensorEvent sensorEvent = this.sensorEventFactory.createSensorEvent("Request","BEAN VALIDATION FAILURE", request.getUserPrincipal().getName());
        sensorEvent.setErrors(ex.getBindingResult().getAllErrors());
        this.sensor.detect(sensorEvent);
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }
}
