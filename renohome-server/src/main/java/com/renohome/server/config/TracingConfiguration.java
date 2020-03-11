package com.renohome.server.config;

import brave.Tracing;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.propagation.ThreadLocalCurrentTraceContext;
import brave.sampler.Sampler;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfiguration {

    @Bean
    Sender sender(@Value("${mcp.zipkin.url}") String zipkinSenderUrl) {
        return OkHttpSender.create(zipkinSenderUrl);
    }

    @Bean
    Reporter<Span> spanReporter(Sender sender) {
        return AsyncReporter.create(sender);
    }

    @Bean
    Tracing tracing(Reporter<Span> spanReporter) {
        return Tracing
                .newBuilder()
                .sampler(Sampler.ALWAYS_SAMPLE)
                .localServiceName("serviceName")
                .propagationFactory(ExtraFieldPropagation
                                            .newFactory(B3Propagation.FACTORY, "client-id"))
                .currentTraceContext(ThreadLocalCurrentTraceContext.create())
                .spanReporter(spanReporter)
                .build();
    }

    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }

}
