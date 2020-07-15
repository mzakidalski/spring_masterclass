package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Instant;

@Log
@RequiredArgsConstructor
public class FakePaymentService implements PaymentService {

    private static final String LOG_FORMAT = "A new payment of %s has been initiated.";

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentRepository repository;


    @Override
    public Payment process(PaymentRequest paymentRequest) {
        var payment = Payment.builder()
                .money(paymentRequest.getMoney())
                .id(paymentIdGenerator.getNext())
                .timestamp(Instant.now())
                .status(PaymentStatus.STARTED)
                .build();

        return repository.save(payment);
    }


    public void init() {
        log.info("PaymentService initialized");
    }

    public void destroy() {
        log.info("Payment service destroyed");
    }

}