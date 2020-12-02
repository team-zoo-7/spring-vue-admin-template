package com.ifsaid.shark.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
@Slf4j
public class AdviceLogging {
    // @formatter:off
    @Before("execution(* com.ifsaid.shark..*.*(..)) "
                    + "&& !execution(* com.ifsaid.shark.SpringRestfulApiApplication..*.*(..)) "
                    + "&& !bean(*Mapper) "
                    + "&& !execution(* com.ifsaid.shark.entity..*.*(..)) "
                    + "&& !execution(* com.ifsaid.shark.common.jwt.JwtAuthenticationTokenFilter.*(..)) "
                    + "&& !execution(* com.ifsaid.shark.common.jwt..*.*(..))"
    )
    // @formatter:on
    public void loggingAdvice(JoinPoint jp) {
//        log.info("\n# START ########## method={}", jp.getSignature());
        log.info("# method={}", jp.getSignature());
        log.info("    args={}", Arrays.toString(jp.getArgs()));
    }

    // @formatter:off
    @AfterReturning(pointcut = "execution(* com.ifsaid.shark..*.*(..)) "
            + "&& !execution(* com.ifsaid.shark.SpringRestfulApiApplication..*.*(..)) "
            + "&& !bean(*Mapper) "
            + "&& !execution(* com.ifsaid.shark.entity..*.*(..)) "
            + "&& !execution(* com.ifsaid.shark.common.jwt.JwtAuthenticationTokenFilter.*(..)) "
            + "&& !execution(* com.ifsaid.shark.common.jwt..*.*(..))"
            , returning = "retVal")
    // @formatter:on
    public void loggingAfterReturning(JoinPoint jp, Object retVal) {
        if (retVal != null) {
            log.info("    retVal={}", retVal.toString());
        }
//        log.info("\n# END   ########## method={}\n", jp.getSignature());
    }
}