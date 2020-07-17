package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.*;
import org.springframework.context.MessageSource;

import java.util.Locale;


@Aspect
@Log
@RequiredArgsConstructor
public class PaymentConsoleLogger {

    private static final String MESSAGE_KEY = "paymentInfo";

    private final MessageSource messageSource;

    @Before(value = "@annotation(pl.training.shop.payments.LogPayments) && args(paymentRequest)")
    public void beforePayment(PaymentRequest paymentRequest) {
        log.info("New payment request: " + paymentRequest);
    }

    @After("@annotation(pl.training.shop.payments.LogPayments)")
    public void afterPayment() {
        log.info("Payment has been finished");
    }

    @AfterThrowing(value = "@annotation(pl.training.shop.payments.LogPayments)", throwing = "ex")
    public void afterThrowing(Exception ex) {
        log.info("Payment exception: " + ex.getClass().getSimpleName());
    }

    @AfterThrowing(value = "@annotation(pl.training.shop.payments.LogPayments)", throwing = "ex")
    public void afterThrowingTwo(RuntimeException ex) {
        log.info("Payment exception:" + ex.getClass().getSimpleName());
    }

    @AfterReturning(value = "@annotation(pl.training.shop.payments.LogPayments)", returning = "payment")
    public void log(Payment payment) {
        log.info(createLogEntry(payment));
    }

    private String createLogEntry(Payment payment) {
        String result =  messageSource.getMessage(MESSAGE_KEY, new String[]{payment.getMoney().toString()},
                Locale.getDefault());
        return result;
    }

}
