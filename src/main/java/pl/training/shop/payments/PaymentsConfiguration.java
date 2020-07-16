package pl.training.shop.payments;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class PaymentsConfiguration {

    @Bean(name = "paymentIdGenerator")
    public PaymentIdGenerator paymentIdGenerator() {
        return new IncrementaPaymentIdGenerator();
    }

    @Bean
    public PaymentRepository paymentRepository() {
        return new HashMapPaymentRepository();
    }

    @Bean
    public PaymentService paymentService(@Qualifier("paymentIdGenerator")
                                                 PaymentIdGenerator paymentIdGenerator,
                                         PaymentRepository paymentRepository,
                                         ApplicationEventPublisher publisher) {
        return new FakePaymentService(paymentIdGenerator, paymentRepository, publisher);
    }

    @Bean
    public PaymentConsoleLogger paymentConsoleLogger(MessageSource messageSource) {
        return new PaymentConsoleLogger(messageSource);
    }


}
