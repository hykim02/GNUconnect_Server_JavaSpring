package com.example.Jinus.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .requestFactory(
                        () -> new BufferingClientHttpRequestFactory(
                                new SimpleClientHttpRequestFactory()
                        )
                ).additionalMessageConverters(
                        new StringHttpMessageConverter(
                                StandardCharsets.UTF_8
                        )
                ).build();
    }
}
