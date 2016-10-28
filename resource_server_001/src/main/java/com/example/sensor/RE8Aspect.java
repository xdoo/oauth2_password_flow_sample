package com.example.sensor;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * @author straubec
 */
@Aspect
@Component
public class RE8Aspect {

    private static final Logger LOG = Logger.getLogger(RE8.class.getName());

    private final SensorClient sensorClient;

    public RE8Aspect(SensorClient sensorClient) {
        this.sensorClient = sensorClient;
    }

    @Before("@annotation(RE8)")
    public void checkParameter(JoinPoint jp) {
        // do some tests here
        // ...
        
        // if something went wrong:
        this.sensorClient.detect();
    }

}
