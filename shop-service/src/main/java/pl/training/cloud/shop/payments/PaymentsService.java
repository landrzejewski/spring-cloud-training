package pl.training.cloud.shop.payments;

import org.javamoney.moneta.FastMoney;

import java.util.Optional;

public interface PaymentsService {

    Optional<Payment> pay(FastMoney value);

}
