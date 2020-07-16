package pl.training.shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.training.shop.payments.IncrementalPaymentIdGenerator;

import static java.lang.Long.parseLong;
import static org.junit.jupiter.api.Assertions.*;

public class IncrementalPaymentIdGeneratorTest {

    private static final String ID_FORMAT = "\\d{10}";

    private final IncrementalPaymentIdGenerator paymentIdGenerator = new IncrementalPaymentIdGenerator();

    @DisplayName("Should generate valid id")
    @Test
    void shouldGenerateValidId() {
        String id = paymentIdGenerator.getNext();
        Assertions.assertTrue(id.matches(ID_FORMAT));
    }

    @Test
    @DisplayName("Should generate id by incrementing value of previous ones")
    void shouldGenerateIdByIncrementingValueOfPreviousOne() {
        long firstIdValue = parseLong(paymentIdGenerator.getNext());
        long secondValue = parseLong(paymentIdGenerator.getNext());
        assertEquals(1L, (secondValue - firstIdValue));
    }

}