package com.sandbox.springsleuth.config;

import brave.SpanCustomizer;
import brave.http.HttpAdapter;
import brave.http.HttpClientParser;

public class CustomHttpClientParser extends HttpClientParser {

    @Override
    public <Req> void request(HttpAdapter<Req, ?> adapter, Req req, SpanCustomizer customizer) {
        customizer.tag("custom2", "custom2");
        super.request(adapter, req, customizer);
    }

    @Override
    public <Resp> void response(HttpAdapter<?, Resp> adapter, Resp res, Throwable error, SpanCustomizer customizer) {
        super.response(adapter, res, error, customizer);
    }
}
