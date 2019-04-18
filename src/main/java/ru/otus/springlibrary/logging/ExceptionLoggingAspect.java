//package ru.otus.springlibrary.logging;
//
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class ExceptionLoggingAspect {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionLoggingAspect.class);
//
//    @AfterThrowing(pointcut = "execution(* ru.otus.springlibrary.service.*.* (..))", throwing = "e")
//    public void logException(Exception e) {
//        LOGGER.error("Exception: " + e);
//    }
//}
