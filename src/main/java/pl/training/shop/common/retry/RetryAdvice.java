package pl.training.shop.common.retry;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;

@Log
@Aspect
@Component
public class RetryAdvice {

    @Around("@annotation(pl.training.shop.common.retry.Retry)")
    public Object retryOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("retryOperation has been called");
        Throwable lastCaughtException = null;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        int numberOfRetries = retrieveNumberOfRetries(joinPoint, signature);
        var args = joinPoint.getArgs();
        for (int i = 1; i <= numberOfRetries; ++i) {
            try {
                log.info(String.format("Retry nr %s", i));
                Object result = joinPoint.proceed(args);
                return result;
            } catch (Throwable ex) {
                log.info("Exception has been thrown");
                lastCaughtException = ex;
            }
        }
        throw lastCaughtException;
    }

    private int retrieveNumberOfRetries(JoinPoint joinPoint, MethodSignature signature) throws NoSuchMethodException {
        final String methodName = signature.getMethod().getName();
        final Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Annotation[] annotations =
                joinPoint.getTarget().getClass().getMethod(methodName,parameterTypes).getAnnotations();
        var retryAnnotation = Arrays.stream(annotations)
                .filter(annotation -> annotation instanceof Retry)
                .findFirst()
                .map(annotation -> (Retry) annotation)
                .orElseThrow(() -> new RuntimeException("Method is not annotated with retry"));

        return retryAnnotation.retries();
    }
}
