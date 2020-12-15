package pl.training.cloud.shop;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Validated
@RequestMapping("orders")
public interface OrdersApi {

    String ORDER_CONFIRMED = "CONFIRMED";
    String ORDER_NOT_CONFIRMED = "NOT_CONFIRMED";

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Void> placeOrder(@Valid @RequestBody OrderTransferObject orderTransferObject);

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    ResponseEntity<OrderTransferObject> getOrder(@PathVariable("id") Long id);

}
