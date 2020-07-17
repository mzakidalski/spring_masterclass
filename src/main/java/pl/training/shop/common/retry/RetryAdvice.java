package pl.training.shop.common.retry;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Log
@Aspect
@Component
public class RetryAdvice {

    @AfterThrowing("@annotation(pl.training.shop.common.retry.Retry)")
    public void retryOperation(JoinPoint joinPoint) throws NoSuchMethodException {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final String methodName = signature.getMethod().getName();
        final Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Annotation[] annotations =
                joinPoint.getTarget().getClass().getMethod(methodName,parameterTypes).getAnnotations();

        log.info("retryOperation has been called");
    }
}
