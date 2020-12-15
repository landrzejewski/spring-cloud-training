package pl.training.commons;

import org.javamoney.moneta.FastMoney;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FastMoneyMapper {

    default FastMoney toFastMoney(String value) {
        return value != null ? FastMoney.parse(value) : LocalMoney.zero();
    }

    default String toValue(FastMoney fastMoney) {
        return fastMoney != null ? fastMoney.toString() : "";
    }

}
