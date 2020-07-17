package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;

import java.util.Locale;

@Aspect
@Log
@RequiredArgsConstructor
@Order(50)
public class PaymentConsoleLogger {

    private static final String MESSAGE_KEY = "paymentInfo";

    private final MessageSource messageSource;

    @Pointcut("@annotation(pl.training.shop.payments.LogPayments)")
    public void logPayments() {}

    @Before(value = "logPayments() && args(paymentRequest)")
    public void beforePayment(PaymentRequest paymentRequest) {
        log.info("New payment request: " + paymentRequest);
    }

    @After("logPayments()")
    public void afterPayment() {
        log.info("After payment");
    }

    @AfterThrowing(value = "logPayments()", throwing = "ex")
    public void afterThrowing(Exception ex) {
        log.info("Payment exception: " + ex.getClass().getSimpleName());
    }

    @AfterThrowing(value = "logPayments()", throwing = "ex")
    public void afterThrowingTwo(RuntimeException ex) {
        log.info("Payment exception:" + ex.getClass().getSimpleName());
    }

    @AfterReturning(value = "logPayments()", returning = "payment")
    public void log(Payment payment) {
        log.info(createLogEntry(payment));
    }

    private String createLogEntry(Payment payment) {
        String result =  messageSource.getMessage(MESSAGE_KEY, new String[]{payment.getMoney().toString()},
                Locale.getDefault());
        return result;
    }

}
