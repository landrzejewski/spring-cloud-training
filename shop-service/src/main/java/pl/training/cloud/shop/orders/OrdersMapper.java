package pl.training.cloud.shop.orders;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.cloud.commons.FastMoneyMapper;
import pl.training.cloud.shop.OrdersApi;
import pl.training.cloud.shop.payments.PaymentStatus;
import pl.training.cloud.shop.products.ProductsService;
import pl.training.cloud.shop.IdTransferObject;
import pl.training.cloud.shop.OrderTransferObject;

import java.util.stream.Collectors;

import static pl.training.cloud.shop.OrdersApi.ORDER_CONFIRMED;
import static pl.training.cloud.shop.OrdersApi.ORDER_NOT_CONFIRMED;

@Mapper(componentModel = "spring", uses = FastMoneyMapper.class)
public abstract class OrdersMapper {


    @Autowired
    @Setter
    private ProductsService productsService;

    public Order toOrder(OrderTransferObject orderTransferObject) {
        var products = orderTransferObject.getProducts().stream()
                .map(IdTransferObject::getId)
                .map(productsService::getById)
                .collect(Collectors.toList());
        return new Order(orderTransferObject.getClientId(), products);
    }

    public OrderTransferObject toOrderTransferObject(Order order) {
        var orderTransferObject = new OrderTransferObject();
        orderTransferObject.setClientId(order.getClientId());
        if (order.getPayment().getStatus() == PaymentStatus.CONFIRMED) {
            orderTransferObject.setStatus(ORDER_CONFIRMED);
        } else {
            orderTransferObject.setStatus(ORDER_NOT_CONFIRMED);
        }
        return orderTransferObject;
    }

}
