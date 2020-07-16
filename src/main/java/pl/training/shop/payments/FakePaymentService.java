package pl.training.shop.payments;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log
@Service
public class FakePaymentService implements PaymentService {

    private static final String LOG_FORMAT = "A new payment of %s has been initiated.";

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentRepository repository;

    public FakePaymentService(@IdGenerator("incrementalPaymentIdGenerator") PaymentIdGenerator paymentIdGenerator,
                              PaymentRepository repository) {
        this.paymentIdGenerator = paymentIdGenerator;
        this.repository = repository;
    }

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