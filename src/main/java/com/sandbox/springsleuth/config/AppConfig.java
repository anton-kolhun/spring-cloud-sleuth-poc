package com.sandbox.springsleuth.config;

import brave.Tracing;
import brave.http.HttpServerParser;
import brave.propagation.B3Propagation;
import brave.propagation.CurrentTraceContext;
import brave.propagation.ExtraFieldPropagation;
import org.springframework.cloud.sleuth.SpanAdjuster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

import java.util.List;

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
        HttpServerParser httpServerParser = new CustomHttpServerParser();
        return httpServerParser;
    }

    /*@Bean
    public HttpClientParser httpClientParser() {
        HttpClientParser httpClientParser =  new CustomHttpClientParser();
        return httpClientParser;
    }*/

    @Bean
    public Tracing tracing(CurrentTraceContext currentTraceContext,
                           Reporter<Span> reporter,
                           List<SpanAdjuster> adjusters) {

        return Tracing.newBuilder()
                .localServiceName("service-name")
                .spanReporter(adjustReporter(reporter, adjusters))
                .propagationFactory(ExtraFieldPropagation.newFactory(B3Propagation.FACTORY, TracingExtraFields.getAllKeys()))
                .currentTraceContext(currentTraceContext)
                .build();
    }

    private static Reporter<Span> adjustReporter(Reporter<Span> reporter, List<SpanAdjuster> adjusters) {
        return span -> {
            Span spanToAdjust = span;
            for (SpanAdjuster adjuster : adjusters) {
                spanToAdjust = adjuster.adjust(spanToAdjust);
            }
            reporter.report(spanToAdjust);
        };
    }

    @Bean
    public SpanAdjuster extraFieldSpanAdjuster() {
        return span -> {
            span.tags().putAll(ExtraFieldPropagation.getAll());
            return span;
        };
    }


}
