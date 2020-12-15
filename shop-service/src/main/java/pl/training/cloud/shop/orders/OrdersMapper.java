package pl.training.cloud.shop.orders;

import lombok.Setter;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.cloud.commons.FastMoneyMapper;
import pl.training.cloud.shop.products.ProductsService;
import pl.training.shop.IdTransferObject;
import pl.training.shop.OrderTransferObject;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {FastMoneyMapper.class})
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

}
