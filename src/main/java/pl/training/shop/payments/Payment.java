package pl.training.shop.payments;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.javamoney.moneta.FastMoney;

import java.time.Instant;

@Builder
@Value
@ToString
@EqualsAndHashCode
public class Payment {
    String id;
    FastMoney money;
    Instant timestamp;
    PaymentStatus status;
}
