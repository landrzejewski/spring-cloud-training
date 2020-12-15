package pl.training.cloud.shop.products;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Log
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Cacheable("products")
    public Product getById(Long id) {
        log.info("Reading from database...");
        return productsRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    @CacheEvict(value = "products", allEntries = true)
    public Product update(Product product) {
        return productsRepository.saveAndFlush(product);
    }

}
