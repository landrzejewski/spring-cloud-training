package pl.training.cloud.payments;

public interface PaymentsService {

    Payment process(PaymentRequest paymentRequest);

    Payment getPayment(String id);

}
