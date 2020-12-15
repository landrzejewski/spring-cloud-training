package pl.training.cloud.shop.payments;


import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.training.cloud.payments.PaymentRequestTransferObject;
import pl.training.cloud.payments.PaymentTransferObject;

import java.net.URI;
import java.util.Optional;

@Transactional
@Service
@Log
@RequiredArgsConstructor
public class RestTemplatePaymentsService implements PaymentsService {

    private static final int PREFERRED_INSTANCE = 0;

    private final PaymentsMapper paymentsMapper;
    private final DiscoveryClient discoveryClient;
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

    private URI getRequestUri() {
        var instances = discoveryClient.getInstances(paymentsServiceName);
        if (instances.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return instances.get(PREFERRED_INSTANCE).getUri().resolve(paymentsResource);
    }

}
