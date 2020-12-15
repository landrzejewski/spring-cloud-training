package pl.training.cloud.payments;

import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import pl.training.cloud.commons.FastMoneyMapper;

@Mapper(componentModel = "spring", uses = FastMoneyMapper.class)
public interface PaymentsMapper {

    PaymentRequest toPaymentRequest(PaymentRequestTransferObject paymentRequestTransferObject);

    PaymentTransferObject toPaymentTransferObject(Payment payment);

    @ValueMapping(source = "STARTED", target = "NOT_CONFIRMED")
    @ValueMapping(source = "FAILED", target = "NOT_CONFIRMED")
    @ValueMapping(source = "CANCELED", target = "NOT_CONFIRMED")
    PaymentStatusTransferObject toPaymentStatusTransferObject(PaymentStatus paymentStatus);

}
