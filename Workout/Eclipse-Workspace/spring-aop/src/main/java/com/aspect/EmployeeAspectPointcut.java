package com.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class EmployeeAspectPointcut {

	@Pointcut("execution(public String getName())")
	public void getNamePointcut() {}
	
	@Pointcut("within(com.service.*)")
	public void allMethodPointcut() {}

	
	@Before("getNamePointcut()")
	public void loggingAdvice() {
		System.out.println("Executing logginAdvice on getName()");
	}
	
	@Before("getNamePointcut()")
	public void secondAdvice() {
		System.out.println("Executing secondAdvice on getName()");
	}
	
	@Before("allMethodPointcut()")
	public void allServiceMethodAdvice() {
		System.out.println("Before executing service methods");
	}
	
}
