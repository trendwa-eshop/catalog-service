package org.trendwa.eshop.catalogservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("execution(* org.trendwa.eshop.catalogservice.controller.*.*(..)) || execution(* org.trendwa.eshop.catalogservice.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Entering method: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "execution(* org.trendwa.eshop.catalogservice.controller.*.*(..)) || execution(* org.trendwa.eshop.catalogservice.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {} with result: {}", joinPoint.getSignature().toShortString(), result);
    }

    @AfterThrowing(pointcut = "execution(* org.trendwa.eshop.catalogservice.controller.*.*(..)) || execution(* org.trendwa.eshop.catalogservice.service.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        log.error("Exception in method: {} with cause: {}", joinPoint.getSignature().toShortString(), error.getCause() != null ? error.getCause() : "NULL");
    }
}