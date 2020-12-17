package pl.training.cloud.commons;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Aspect
@Service
@Log
public class ExecutionTimeLogger {

    @Around("@annotation(LogExecutionTime)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.nanoTime();
        var result = joinPoint.proceed();
        var totalTime= System.nanoTime() - startTime;
        log.info(String.format("%s executed in %d ns", joinPoint.getSignature(), totalTime));
        return result;
    }

}
