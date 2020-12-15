package pl.training.cloud.shop.payments;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import pl.training.cloud.payments.PaymentStatusTransferObject;
import pl.training.cloud.payments.PaymentTransferObject;

@Mapper(componentModel = "spring")
public interface PaymentsMapper {

    Payment toPayment(PaymentTransferObject paymentTransferObject);

    @ValueMapping(source = "NOT_CONFIRMED", target = "STARTED")
    PaymentStatus toPaymentStatus(PaymentStatusTransferObject paymentStatusTransferObject);

}
