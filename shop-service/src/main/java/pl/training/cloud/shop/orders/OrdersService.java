package pl.training.cloud.shop.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public void placeOrder(Order order) {
        var paymentValue = order.getTotalValue();

        ordersRepository.saveAndFlush(order);
    }

    public Order getById(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);
    }

}
