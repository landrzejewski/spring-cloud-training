package pl.training.cloud.shop.commons;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FeignTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaders.AUTHORIZATION, getToken());
    }

    public String getToken() {
        var authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        var principal = (KeycloakPrincipal) authentication.getPrincipal();
        return "Bearer " + principal.getKeycloakSecurityContext().getTokenString();
    }

}
