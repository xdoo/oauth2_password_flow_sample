package com.example.sensor;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
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
public class SensorAspect {

    private static final Logger LOG = Logger.getLogger(RE8.class.getName());

    private final SensorClient sensorClient;
    
    private final SensorEventFactory sensorEventFactory;

    public SensorAspect(SensorClient sensorClient, SensorEventFactory sensorEventFactory) {
        this.sensorClient = sensorClient;
        this.sensorEventFactory = sensorEventFactory;
    }

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {
    }

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
        LOG.info("foo");
    }

    @AfterThrowing(pointcut = "controller() && allMethod()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        LOG.log(Level.INFO, "An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
        LOG.log(Level.INFO, "Cause : " + exception.getCause());
    }

}
