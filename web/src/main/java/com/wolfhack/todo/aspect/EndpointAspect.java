package com.wolfhack.todo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class EndpointAspect {

	@Pointcut("@annotation(com.wolfhack.todo.annotation.Endpoint)")
	public void processEndpoint() {
	};

	@Around("processEndpoint()")
	public Object execEndpointEntryPoint(ProceedingJoinPoint joinPoint) throws Throwable {
		final StopWatch stopWatch = new StopWatch();

		stopWatch.start();
		Object retVal = joinPoint.proceed();
		stopWatch.stop();

		log.info("Class {} operation {} executed in {}ms",
				joinPoint.getTarget().getClass().getSimpleName(),
				joinPoint.getSignature().getName(),
				stopWatch.getTotalTimeMillis());

		return retVal;
	}

	@AfterThrowing(pointcut = "processEndpoint()", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
		log.error("Exception in class {} operation {}. Cause: {}", joinPoint.getTarget().getClass().getSimpleName(),
				joinPoint.getSignature().getName(), exception.getCause() != null ? exception.getCause() : "NULL");
	}
}
