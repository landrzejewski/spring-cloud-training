package pl.training.cloud.payments;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentTransferObject {

    private String id;
    private String value;
    private LocalDateTime timestamp;
    private PaymentStatusTransferObject status;

}
