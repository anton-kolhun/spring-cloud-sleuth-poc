package com.sandbox.springsleuth.config;

import brave.ScopedSpan;
import brave.Tracer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TracingAspect {

    @Autowired
    private Tracer tracer;

    @Around("within(org.springframework.data.jpa.repository.JpaRepository+)" +
            " || execution(public * com.sandbox.springsleuth.service.*.*(..))")
    public Object traceAround(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String className = pjp.getSignature().getDeclaringType().getSimpleName();
        //final String parameters = Arrays.toString(pjp.getArgs());

        ScopedSpan span = tracer.startScopedSpan(className + "." + methodName)
                .tag("class", pjp.getSignature().getDeclaringType().getCanonicalName())
                .tag("method", methodName);


        Object retVal = pjp.proceed();
        span.finish();

        return retVal;
    }


}