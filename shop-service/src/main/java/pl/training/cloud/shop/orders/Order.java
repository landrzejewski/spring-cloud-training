package pl.training.cloud.shop.orders;

import lombok.*;
import org.javamoney.moneta.FastMoney;
import pl.training.cloud.commons.LocalMoney;
import pl.training.cloud.shop.products.Product;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Order {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private Long clientId;
    @ManyToMany
    @NonNull
    private List<Product> products;

    public FastMoney getTotalValue() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(LocalMoney.zero(), FastMoney::add);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Order)) {
            return false;
        }
        Order otherOrder = (Order) otherObject;
        return Objects.equals(id, otherOrder.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
