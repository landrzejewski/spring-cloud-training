package pl.training.cloud.commons;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

@Aspect
@Service
@Log
public class MethodRepeater {

    @Around("@annotation(Retry)")
    public Object tryInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        var retryAnnotation = AnnotationUtils.findAnnotation(method, Retry.class);
        var attempts = retryAnnotation.attempts();
        var currentAttempt = 0;
        Throwable throwable;
        do {
            currentAttempt++;
            log.info(String.format("%s execution attempt %d", method.getName(), currentAttempt));
            try {
                return joinPoint.proceed();
            } catch (Throwable currentThrowable) {
                throwable = currentThrowable;
            }
        } while (currentAttempt < attempts);
        throw throwable;
    }

}
