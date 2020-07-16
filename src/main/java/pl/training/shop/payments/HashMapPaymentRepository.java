package pl.training.shop.payments;

import lombok.extern.java.Log;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log
@Repository
public class HashMapPaymentRepository implements  PaymentRepository {

    private final Map<String, Payment> internalMap = new ConcurrentHashMap<>();

    @Override
    public Payment save(Payment payment) {
         if (payment != null && payment.getId() != null) {
            internalMap.put(payment.getId(), payment);
            log.info(String.format("Payment %s has been successfully saved.", payment));
         } else {
             log.warning("Payment object is null OR its id is null.");
         }
         return payment;
    }

}
