package com.sandbox.springsleuth.config;

import brave.http.HttpClientParser;
import brave.http.HttpServerParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*@Bean
    public Sampler sampler() {
        return Sampler.ALWAYS_SAMPLE;
    }*/


    @Bean
    public HttpServerParser httpServerParser() {
        HttpServerParser httpServerParser =  new CustomHttpServerParser();
        return httpServerParser;
    }

    @Bean
    public HttpClientParser httpClientParser() {
        HttpClientParser httpClientParser =  new CustomHttpClientParser();
        return httpClientParser;
    }

}
