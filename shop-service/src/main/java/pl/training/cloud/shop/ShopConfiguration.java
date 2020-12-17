package pl.training.cloud.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.training.cloud.commons.LocalMoney;
import pl.training.cloud.shop.commons.TokenInterceptor;
import pl.training.cloud.shop.payments.PaymentsSink;
import pl.training.cloud.shop.products.Product;
import pl.training.cloud.shop.products.ProductsRepository;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.List;

@ComponentScan("pl.training.cloud.commons")
@EnableFeignClients("pl.training.cloud.payments")
@EnableBinding({Sink.class, PaymentsSink.class})
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

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .interceptors(List.of(new TokenInterceptor()))
                .build();
    }

}
