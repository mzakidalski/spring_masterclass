package pl.training.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationEventPublisher;
import pl.training.shop.common.profiler.ExecutionTime;

import java.time.Instant;

@Log
@RequiredArgsConstructor
public class FakePaymentService implements PaymentService {

    private static final String LOG_FORMAT = "A new payment of %s has been initiated.";

    private final PaymentIdGenerator paymentIdGenerator;
    private final PaymentRepository repository;
    private final ApplicationEventPublisher publisher;

    @ExecutionTime
    @LogPayments
    @Override
    public Payment process(PaymentRequest paymentRequest) {
        var payment = Payment.builder()
                .money(paymentRequest.getMoney())
                .id(paymentIdGenerator.getNext())
                .timestamp(Instant.now())
                .status(PaymentStatus.STARTED)
                .build();

        publisher.publishEvent(new PaymentStatusChangedEvent(this, payment));
        return repository.save(payment);
    }

}