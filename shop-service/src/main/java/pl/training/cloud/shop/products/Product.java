package pl.training.cloud.shop.products;


import lombok.*;
import org.javamoney.moneta.FastMoney;
import pl.training.cloud.commons.FastMoneyConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Product {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private String name;
    @Convert(converter = FastMoneyConverter.class)
    @NonNull
    private FastMoney price;

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Product)) {
            return false;
        }
        Product product = (Product) otherObject;
        return Objects.equals(id, product.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
