package com.ifsaid.shark.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Aspect
@Component
@Slf4j
public class CentralLoggingHandler {
    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) && @annotation(mapping)")
    public void logControllerAccess(RequestMapping mapping) {
//                log.info("########## START ########## method={}", jp.getSignature());.info("Executing {} request", mapping.value()[0]);
    }

    @Before("execution(* com.ifsaid.shark.*..*Service+.*(..)) && target(service)")
    public void loggerServiceAccess(Object service) {
//        log.info("Accessing {}", service.getClass().getSimpleName());
    }

    //    @AfterReturning(value = "@target(org.springframework.stereotype.Repository) && !execution(* get*(..))", returning = "returnValue")
    //    public void loggingRepositoryMethods(JoinPoint joinPoint, Object returnValue) {
    //        if (returnValue != null) {
    //                    log.info("########## START ########## method={}", jp.getSignature());.info("  retVal {}", returnValue);
    //        }
    //    }

    //    @AfterReturning(pointcut = "execution(* Operation.*(..))", returning = "result")
    //    public void myadvice(JoinPoint jp, Object result) {
    //        if (result != null) {
    //                    log.info("########## START ########## method={}", jp.getSignature());.info("  retVal {}", result);
    //        }
    //    }
}
