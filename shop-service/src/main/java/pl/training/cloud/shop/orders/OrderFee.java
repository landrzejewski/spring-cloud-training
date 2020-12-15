package pl.training.cloud.shop.orders;

import lombok.Getter;
import lombok.extern.java.Log;
import org.javamoney.moneta.FastMoney;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.training.cloud.commons.LocalMoney;

@RefreshScope
@Component
@Log
public class OrderFee {

    @Getter
    private FastMoney value;

    @Value("${order-fee}")
    public void setValue(long value) {
        log.info("Updating order fee to: " + value);
        this.value = LocalMoney.of(value);
    }

}
