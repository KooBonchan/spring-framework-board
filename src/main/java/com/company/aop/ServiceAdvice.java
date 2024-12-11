package com.company.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class ServiceAdvice {
	/*
	 * @Before("execution(* com.company.service.*.*(..))") public void
	 * logger(JoinPoint jp) { log.info("------AspectJ Test-------");
	 * log.info("SIGN: " + jp.getSignature()); log.info("ARGS: " +
	 * Arrays.toString(jp.getArgs())); }
	 */
	
}
