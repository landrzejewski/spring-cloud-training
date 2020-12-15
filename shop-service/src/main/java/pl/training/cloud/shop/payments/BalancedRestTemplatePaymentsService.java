package pl.training.cloud.shop.payments;


import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.training.cloud.payments.PaymentRequestTransferObject;
import pl.training.cloud.payments.PaymentTransferObject;

import java.util.Optional;

@Service
@Log
@RequiredArgsConstructor
public class BalancedRestTemplatePaymentsService implements PaymentsService {

    private final PaymentsMapper paymentsMapper;
    private final RestTemplate restTemplate;

    @Value("${payments-service.name}")
    @Setter
    private String paymentsServiceName;
    @Value("${payments-service.resource}")
    @Setter
    private String paymentsResource;

    @Override
    public Optional<Payment> pay(FastMoney value) {
        var paymentRequestTransferObject = new PaymentRequestTransferObject(value.toString());
        try {
            var paymentTransferObject = restTemplate.postForObject(getRequestUri(), paymentRequestTransferObject, PaymentTransferObject.class);
            if (paymentTransferObject == null) {
                return Optional.empty();
            }
            return Optional.of(paymentsMapper.toPayment(paymentTransferObject));
        } catch (HttpClientErrorException exception) {
            log.warning("Payment failed: " + exception.getMessage());
        }
        return Optional.empty();
    }

    private String getRequestUri() {
        return "http://" + paymentsServiceName + paymentsResource;
    }

}
