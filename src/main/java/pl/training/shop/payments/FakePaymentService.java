package pl.training.shop.payments;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Instant;

@Log
@Scope(BeanDefinition.SCOPE_SINGLETON)
@Service("paymentService")
public class FakePaymentService implements PaymentService {

    private static final String LOG_FORMAT = "A new payment of %s has been initiated.";

    private PaymentIdGenerator paymentIdGenerator;
    private PaymentRepository repository;

    @Autowired
    public FakePaymentService(@IdGenerator("incremental") PaymentIdGenerator paymentIdGenerator,
                              PaymentRepository repository) {
        log.info("Constructor called");
        this.paymentIdGenerator = paymentIdGenerator;
        this.repository = repository;
    }

    @LogPayments
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

    public void setPaymentIdGenerator(PaymentIdGenerator paymentIdGenerator) {
        this.paymentIdGenerator = paymentIdGenerator;
    }

    public void setRepository(PaymentRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        log.info("PaymentService initialized");
    }

    @PreDestroy
    public void destroy() {
        log.info("Payment service destroyed");
    }

}