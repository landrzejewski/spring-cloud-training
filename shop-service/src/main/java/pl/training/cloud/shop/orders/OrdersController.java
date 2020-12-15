package pl.training.cloud.shop.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.training.cloud.shop.OrderTransferObject;
import pl.training.cloud.shop.OrdersApi;

import static pl.training.cloud.commons.UriBuilder.requestUriWithId;

@RestController
@RequiredArgsConstructor
public class OrdersController implements OrdersApi {

    private final OrdersMapper ordersMapper;
    private final OrdersService ordersService;

    @Override
    public ResponseEntity<Void> placeOrder(OrderTransferObject orderTransferObject) {
        var order = ordersMapper.toOrder(orderTransferObject);
        ordersService.placeOrder(order);
        var uri = requestUriWithId(order.getId());
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<OrderTransferObject> getOrder(Long id) {
        var order = ordersService.getById(id);
        return ResponseEntity.ok(ordersMapper.toOrderTransferObject(order));
    }

}
