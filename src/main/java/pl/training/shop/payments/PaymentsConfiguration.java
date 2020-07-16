package pl.training.shop.payments;


public class PaymentsConfiguration {

    public PaymentIdGenerator incrementalPaymentIdGenerator() {
        return new IncrementalPaymentIdGenerator();
    }

    public PaymentIdGenerator uuidPaymentIdGenerator() {
        return new UUIDPaymentIdGenerator();
    }

    public PaymentRepository paymentRepository(){
        return new HashMapPaymentRepository();
    }

    public PaymentService fakePaymentService(PaymentIdGenerator paymentIdGenerator,
                                             PaymentRepository paymentRepository) {
        return new FakePaymentService(paymentIdGenerator, paymentRepository);
    }

    public PaymentConsoleLogger paymentConsoleLogger() {
        return new PaymentConsoleLogger();
    }


}
