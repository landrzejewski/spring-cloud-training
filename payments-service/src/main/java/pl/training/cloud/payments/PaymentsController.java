package pl.training.cloud.payments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static pl.training.cloud.commons.UriBuilder.requestUriWithId;


@RestController
@RequiredArgsConstructor
public class PaymentsController implements PaymentsApi {

    private final PaymentsMapper paymentsMapper;
    private final PaymentsService paymentsService;

    @Override
    public ResponseEntity<PaymentTransferObject> process(PaymentRequestTransferObject paymentRequestTransferObject) {
        var paymentRequest = paymentsMapper.toPaymentRequest(paymentRequestTransferObject);
        var payment = paymentsService.process(paymentRequest);
        var locationUri = requestUriWithId(payment.getId());
        return ResponseEntity.created(locationUri)
                .body(paymentsMapper.toPaymentTransferObject(payment));
    }

    @Override
    public ResponseEntity<PaymentTransferObject> getPayment(String id) {
        var payment = paymentsService.getPayment(id);
        return ResponseEntity.ok(paymentsMapper.toPaymentTransferObject(payment));
    }

    /*@ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Void> onPaymentNotFound(PaymentNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }*/

}
