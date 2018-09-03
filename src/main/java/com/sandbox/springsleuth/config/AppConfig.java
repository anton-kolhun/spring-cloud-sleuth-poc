package com.sandbox.springsleuth.config;

import brave.http.HttpServerParser;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.propagation.Propagation;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableDiscoveryClient
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public TracingAspect tracingAspect() {
        return new TracingAspect();
    }

    /*@Bean
    public Sampler sampler() {
        return Sampler.ALWAYS_SAMPLE;
    }*/


    @Bean
    public HttpServerParser httpServerParser() {
        HttpServerParser httpServerParser = new CustomHttpServerParser();
        return httpServerParser;
    }

    /*@Bean
    public HttpClientParser httpClientParser() {
        HttpClientParser httpClientParser =  new CustomHttpClientParser();
        return httpClientParser;
    }*/


    @Bean
    public Propagation.Factory sleuthPropagation() {
        return ExtraFieldPropagation.newFactory(B3Propagation.FACTORY, TracingExtraFields.getAllKeys());
    }

    @Bean
    public SpanAdjuster extraFieldSpanAdjuster() {
        return span -> {
            span.tags().putAll(ExtraFieldPropagation.getAll());
            return span;
        };
    }


}
