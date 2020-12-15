package pl.training.cloud.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.training.cloud.commons.LocalMoney;
import pl.training.cloud.shop.products.Product;
import pl.training.cloud.shop.products.ProductsRepository;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@ComponentScan("pl.training.cloud.commons")
@EnableSwagger2
@EnableCaching
@Configuration
public class ShopConfiguration {

    @Autowired
    private ProductsRepository productsRepository;

    @PostConstruct
    public void setup() {
        productsRepository.saveAndFlush(new Product("Spring in action", LocalMoney.of(200)));
        productsRepository.saveAndFlush(new Product("Angular in action", LocalMoney.of(100)));
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pl.training.cloud.shop"))
                .build();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("products");
    }

}
