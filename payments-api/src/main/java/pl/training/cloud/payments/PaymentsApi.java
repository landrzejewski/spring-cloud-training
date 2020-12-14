package pl.training.cloud.payments;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Validated
@RequestMapping("payments")
public interface PaymentsApi {

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<PaymentTransferObject> process(@Valid @RequestBody PaymentRequestTransferObject paymentRequestTransferObject);

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    ResponseEntity<PaymentTransferObject> getPayment(@PathVariable("id") String id);

}
