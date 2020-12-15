package pl.training.cloud.commons;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriBuilder {

    public static URI requestUriWithId(Object id) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

}
