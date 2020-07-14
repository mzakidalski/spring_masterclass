package pl.training.shop;

import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.training.shop.payments.IdGenerator;
import pl.training.shop.payments.PaymentIdGenerator;

@IdGenerator("incremental")
public class IncrementaPaymentIdGenerator implements PaymentIdGenerator {

    public static final String ID_FORMAT = "%010d";

    @Setter
    private long index;

    @Override
    public String getNext() {
        return String.format(ID_FORMAT, ++index);
    }
}