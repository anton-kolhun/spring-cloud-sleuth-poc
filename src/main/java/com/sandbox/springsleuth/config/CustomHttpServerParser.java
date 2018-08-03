package com.sandbox.springsleuth.config;

import brave.SpanCustomizer;
import brave.http.HttpAdapter;
import brave.http.HttpServerParser;

public class CustomHttpServerParser extends HttpServerParser {

    @Override
    public <Req> void request(HttpAdapter<Req, ?> adapter, Req req, SpanCustomizer customizer) {
        customizer.tag("tag-name", "tag-value");
        super.request(adapter, req, customizer);
    }

    @Override
    public <Resp> void response(HttpAdapter<?, Resp> adapter, Resp res, Throwable error, SpanCustomizer customizer) {
        super.response(adapter, res, error, customizer);
    }
}
