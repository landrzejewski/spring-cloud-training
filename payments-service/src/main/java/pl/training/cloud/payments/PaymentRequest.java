package pl.training.cloud.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.FastMoney;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private FastMoney value;
    private Map<String, String> properties;

}
