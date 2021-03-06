package pl.training.shop.orders;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;
import pl.training.shop.payments.LocalMoney;
import pl.training.shop.payments.Payment;
import pl.training.shop.products.Product;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Order {

    private Long id;
    @NonNull
    @NotEmpty
    private List<Product> products;
    private Payment payment;

    public FastMoney getTotalPrice() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(LocalMoney.zero(), FastMoney::add);
    }
}
