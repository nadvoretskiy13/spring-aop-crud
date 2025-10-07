package ru.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
  private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

  @Pointcut("execution(public * ru.example.service.*.*(..))")
  public void servicePublicMethods() {}

  @Around("servicePublicMethods()")
  public Object aroundService(ProceedingJoinPoint pjp) throws Throwable {
    String signature = pjp.getSignature().toShortString();
    log.info("Enter: {}", signature);
    long start = System.currentTimeMillis();
    try {
      Object result = pjp.proceed();
      long time = System.currentTimeMillis() - start;
      log.info("Exit: {} ({} ms)", signature, time);
      return result;
    } catch (Throwable t) {
      log.error("Exception in {} : {}", signature, t.getMessage());
      throw t;
    }
  }

  @Before("within(ru.example.controller..*) && execution(* *(..))")
  public void beforeController() {
    log.debug("Controller method called");
  }
}
