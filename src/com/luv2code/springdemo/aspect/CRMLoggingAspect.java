package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	//setup logger
	Logger myLogger=Logger.getLogger(getClass().getName());
	
	//setup pointcut declarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	//do same for service package and dao package
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {}
	
	//add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		
		//display the method we are calling
		String theMethod=theJoinPoint.getSignature().toShortString();
		myLogger.info("\n====> in @Before calling method: "+theMethod);
		
		//display the arguments of the method
		
		
		//get the arguments
		Object[] args=theJoinPoint.getArgs();
		
		
		//loop through and display arguments
		for(Object tempArg:args) {
			myLogger.info("=====> argument: "+tempArg);
		}
		
		
	}
	
	
	
	//add @AfterReturning advice
	@AfterReturning(pointcut = "forAppFlow()",returning = "theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		
		//display whcih method we are in 
		String theMethod=theJoinPoint.getSignature().toShortString();
		myLogger.info("\n====> in @AfterReturning calling method: "+theMethod);
		
		//display the data returned
		myLogger.info("=====> result: "+theResult);
		
	}
	

}
