package com.revature.autosurvey.aspects;

import java.util.Arrays;
import java.util.Formatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Component
@Aspect
public class LoggingAspect {

	@Around("everything()")
	public Object log(ProceedingJoinPoint pjp) throws Throwable {
		// ProceedingJoinPoint - Object representation of the method being called.
		Object result = null;
		Logger log = LogManager.getLogger(pjp.getTarget().getClass());
		log.trace("Method with signature: %s", pjp.getSignature());
		log.trace("with arguments: %s", Arrays.toString(pjp.getArgs()));
		try {
			result = pjp.proceed();
		} catch (Throwable t) {
			log.error("Method threw exception: %s", t);
			for (StackTraceElement s : t.getStackTrace()) {
				log.warn(s);
			}
			if (t.getCause() != null) {
				Throwable t2 = t.getCause();
				log.error("Method threw wrapped exception: %s", t2);
				for (StackTraceElement s : t2.getStackTrace()) {
					log.warn(s);
				}
			}
			throw t; // we don't want our proxy to have the side-effect of
			// stopping the exception from being thrown (it needs to be handled elsewhere)
			// but we do want to log it for ourselves.
		}
		Formatter formatter = new Formatter();
		formatter.format("Method returning with: %s", result);
		log.trace(formatter);
		return result;
	}

	// hook - a method that only exists as the target for an annotation
	@Pointcut("execution( * com.revature.autosurvey..*(..) )")
	private void everything() {
		/* Empty method for hook */ }
}

