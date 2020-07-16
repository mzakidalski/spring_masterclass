package pl.training.shop.orders;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class OrdersConfiguration {

    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public OrderRepository orderRepository() {
        return new HashMapOrderRepository();
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository) {
        return new OrderService(orderRepository);
    }
}
