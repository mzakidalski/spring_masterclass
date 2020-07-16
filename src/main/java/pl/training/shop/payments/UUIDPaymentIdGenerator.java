package pl.training.shop.payments;

import org.springframework.stereotype.Service;

import java.util.UUID;

@IdGenerator("uuid")
@Service
public class UUIDPaymentIdGenerator implements PaymentIdGenerator {
    @Override
    public String getNext() {
        return UUID.randomUUID().toString();
    }
}
