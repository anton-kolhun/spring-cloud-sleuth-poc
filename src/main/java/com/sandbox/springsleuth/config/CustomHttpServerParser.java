package com.sandbox.springsleuth.config;

import brave.SpanCustomizer;
import brave.Tracer;
import brave.http.HttpAdapter;
import brave.http.HttpServerParser;
import brave.propagation.ExtraFieldPropagation;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomHttpServerParser extends HttpServerParser {

    @Autowired
    private Tracer tracer;


    @Override
    public <Req> void request(HttpAdapter<Req, ?> adapter, Req req, SpanCustomizer customizer) {
        //populates keys at controller layer
        customizer.tag(TracingExtraFields.KEY1.getKey(), "value1");
        customizer.tag(TracingExtraFields.KEY2.getKey(), "value2");

        //sets tracer.currentSpan().context.extra.values that will be used for all subsequent spans.
        ExtraFieldPropagation.set(tracer.currentSpan().context(), TracingExtraFields.KEY1.getKey(), "value1");
        ExtraFieldPropagation.set(tracer.currentSpan().context(), TracingExtraFields.KEY2.getKey(), "value2");
        super.request(adapter, req, customizer);
    }

    @Override
    public <Resp> void response(HttpAdapter<?, Resp> adapter, Resp res, Throwable error, SpanCustomizer customizer) {
        super.response(adapter, res, error, customizer);
    }
}
