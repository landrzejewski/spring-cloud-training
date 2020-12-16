package pl.training.cloud.shop.payments;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentsSink {

    String INPUT = "payments";

    @Input(INPUT)
    SubscribableChannel channel();

}
