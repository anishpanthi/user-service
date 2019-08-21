package com.app.api.aspects;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Anish Panthi
 */
@Component
@Aspect
@Log4j2
public class LoggingAspects {

    @Pointcut("within(com.app.api..*)"
            + "&& !@annotation(com.app.api.annotation.EnableEscapeForCGLibProxy)"
            + "&& !@target(com.app.api.annotation.EnableEscapeForCGLibProxy)")
    public void logExceptionForAllMethods() {
        log.debug("Pointcut method to log all exceptions that occurs in any method.");
    }

    @AfterThrowing(pointcut = "logExceptionForAllMethods()", throwing = "exception")
    public void afterThrowingException(JoinPoint joinPoint, Exception exception) {
        String errorMessage = "Exception Occurred:: ";
        errorMessage += " -- Class:: " + joinPoint.getSignature().getDeclaringTypeName();
        errorMessage += " -- Method:: " + joinPoint.getSignature().getName();
        errorMessage += " -- Root Cause:: " + exception;
        errorMessage += " -- FullStackTrace:: " + exception.fillInStackTrace();

        log.error("{}", errorMessage);
    }
}
