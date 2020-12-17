package pl.training.cloud.shop.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.training.cloud.commons.Retry;
import pl.training.cloud.shop.payments.PaymentsService;

@Transactional
@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final PaymentsService paymentsService;
    private final OrderFee orderFee;

    @Retry(attempts = 4)
    public void placeOrder(Order order) {
        var paymentValue = order.getTotalValue().add(orderFee.getValue());
        var payment = paymentsService.pay(paymentValue)
                .orElseThrow(PaymentInitializationException::new);
        order.setPayment(payment);
        ordersRepository.saveAndFlush(order);
    }

    public Order getById(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
    }

}
