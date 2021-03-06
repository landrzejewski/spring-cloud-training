package pl.training.cloud.payments;

import lombok.*;
import org.javamoney.moneta.FastMoney;
import pl.training.cloud.commons.FastMoneyConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

// https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    private String id;
    private LocalDateTime timestamp;
    @Convert(converter = FastMoneyConverter.class)
    private FastMoney value;
    private PaymentStatus status;

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Payment)) {
            return false;
        }
        Payment payment = (Payment) otherObject;
        return Objects.equals(id, payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
