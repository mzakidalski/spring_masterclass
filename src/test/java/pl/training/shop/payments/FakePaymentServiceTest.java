package pl.training.shop.payments;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FakePaymentServiceTest {

    private static final String PAYMENT_ID = "1";
    private static final FastMoney MONEY = LocalMoney.of(1_000);
    private static final PaymentRequest PAYMENT_REQUEST = PaymentRequest.builder()
            .money(MONEY)
            .build();

    @Mock
    PaymentRepository repository;

    @Mock
    private PaymentIdGenerator paymentIdGenerator;
    private Payment payment;

    @BeforeEach
    void setUp() {
        when(repository.save(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
        when(paymentIdGenerator.getNext()).thenReturn(PAYMENT_ID);
        FakePaymentService fakePaymentService = new FakePaymentService(paymentIdGenerator, repository);
//        fakePaymentService.setPaymentIdGenerator(paymentIdGenerator);
//        fakePaymentService.setRepository(repository);

        payment = fakePaymentService.process(PAYMENT_REQUEST);
    }

    @DisplayName("Should assign generated id to created payment")
    @Test
    void shouldAssignGeneratedIdToCreatedPayment() {
        assertEquals(PAYMENT_ID, payment.getId());
        verify(repository).save(payment);
    }

    @DisplayName("Should assign money from request to created payment")
    @Test
    void shouldAssignMoenyFromPaymentRequestToCreatedPayment() {
        assertEquals(MONEY, payment.getMoney());
        verify(repository).save(payment);
    }

    @DisplayName("Should assign timestamp to created payment")
    @Test
    void shouldAssignTimestampToCreatedPayment() {
        assertNotNull(payment.getTimestamp());
        verify(repository).save(payment);
    }

    @DisplayName("Should assign STARTED status to created payment")
    @Test
    void shouldAssignStartedStatusToCreatedPayment() {
        assertEquals(PaymentStatus.STARTED, payment.getStatus());
        verify(repository).save(payment);
    }
}