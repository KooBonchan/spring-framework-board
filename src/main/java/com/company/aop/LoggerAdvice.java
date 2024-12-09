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
public class LoggerAdvice {
	
	@Before("execution(* com.company.service.*.*(..))")
	public void startLog(JoinPoint jp) {
		log.info("-----SERVICE------");
		log.info("Parameter: " + Arrays.toString(jp.getArgs()));
		log.info("Signature: " + jp.getSignature().getName());
	}
	
}
