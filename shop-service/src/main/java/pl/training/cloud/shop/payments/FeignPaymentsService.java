package pl.training.cloud.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.training.cloud.payments.PaymentRequestTransferObject;
import pl.training.cloud.payments.PaymentTransferObject;
import pl.training.cloud.payments.PaymentsApi;

import java.util.Optional;

@Primary
@Service
@Log
@RequiredArgsConstructor
public class FeignPaymentsService implements PaymentsService {

    private final PaymentsApi paymentsApi;
    private final PaymentsMapper paymentsMapper;
    private final PaymentsRepository paymentsRepository;

    @Override
    public Optional<Payment> pay(FastMoney value) {
        var paymentRequestTransferObject = new PaymentRequestTransferObject(value.toString());
        try {
            var response = paymentsApi.process(paymentRequestTransferObject);
            return Optional.of(paymentsMapper.toPayment(response.getBody()));
        } catch (HttpClientErrorException exception) {
            log.warning("Payment failed: " + exception.getMessage());
        }
        return Optional.empty();
    }

    @StreamListener(Sink.INPUT)
    public void updatePaymentStatus(PaymentTransferObject paymentTransferObject) {
        log.info("Payment status update: " + paymentTransferObject);
        var payment = paymentsMapper.toPayment(paymentTransferObject);
        paymentsRepository.saveAndFlush(payment);
    }

    @StreamListener(Sink.INPUT)
    public void updatePaymentStatus(PaymentTransferObject paymentTransferObject) {
        log.info("Payment status update: " + paymentTransferObject);
        var payment = paymentsMapper.toPayment(paymentTransferObject);
        paymentsRepository.saveAndFlush(payment);
    }

}
