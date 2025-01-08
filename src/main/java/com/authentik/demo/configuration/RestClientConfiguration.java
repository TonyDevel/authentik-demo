package com.authentik.demo.configuration;

import com.authentik.demo.configuration.properties.AuthentikConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient authentikRestClient(AuthentikConfigurationProperties properties) {
        return RestClient.builder()
            .baseUrl(properties.getBaseUrl())
            .requestFactory(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()))
            .defaultHeaders(headers -> {
                headers.setBearerAuth(properties.getApiToken());
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            })
            .build();
    }
}
