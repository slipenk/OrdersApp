package com.slipenk.ordersapp.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import static com.slipenk.ordersapp.dictionary.Dictionary.ARGUMENT;
import static com.slipenk.ordersapp.dictionary.Dictionary.BEFORE_METHOD;
import static com.slipenk.ordersapp.dictionary.Dictionary.RESULT;
import static com.slipenk.ordersapp.dictionary.Dictionary.RETURN_FROM_METHOD;

@Aspect
@Component
@Log
public class LoggingAspect {

    @Pointcut("execution(* com.slipenk.ordersapp.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* com.slipenk.ordersapp.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("forControllerPackage() || forServicePackage()")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    public void beforeAdvice(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        log.info(BEFORE_METHOD + method);

        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            log.info(ARGUMENT + arg);
        }

    }

    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        log.info(RETURN_FROM_METHOD + method);

        log.info(RESULT + result);
    }

}
