package pl.training.shop.payments;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class HashMapPaymentRepositoryTest {

    private final PaymentRepository repository = new HashMapPaymentRepository();

    @Test
    void processNullsCorrectly() {
        repository.save(null);
    }

    @Test
    void savesNonNullPayment() {
        final Payment payment = Payment.builder()
                .id("1")
                .money(LocalMoney.of(1_000))
                .status(PaymentStatus.STARTED)
                .build();
        Assertions.assertEquals(payment, repository.save(payment));
    }

}