package pl.training.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.training.shop.orders.OrderService;
import pl.training.shop.orders.OrdersConfiguration;
import pl.training.shop.payments.PaymentService;
import pl.training.shop.payments.PaymentsConfiguration;
import pl.training.shop.products.ProductService;
import pl.training.shop.products.ProductsConfiguration;

@Configuration
@Import(value = {OrdersConfiguration.class, PaymentsConfiguration.class, ProductsConfiguration.class})
public class ApplicationConfiguration {

    @Bean
    public ShopService shopService(OrderService orderService, PaymentService paymentService,
                                   ProductService productService) {
        return new ShopService(orderService, paymentService, productService);
    }
}
