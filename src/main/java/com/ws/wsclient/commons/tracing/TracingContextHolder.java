package com.ws.wsclient.commons.tracing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public  final  class TracingContextHolder {
    private static final Logger LOG = LoggerFactory.getLogger(TracingContextHolder.class);
    private static final ThreadLocal<TracingContext> threadLocal = new ThreadLocal();

    private TracingContextHolder() {
    }

    public static TracingContext get() {
        return (TracingContext)threadLocal.get();
    }

    public static TracingContext initialize() {
        if (threadLocal.get() != null) {
            LOG.warn("During initialization of a new tracing context, one was already associated with the thread. This is likely due to an improper clean up of the tracing context. This could lead to memory leaks!");
        }

        TracingContext tracingContext = new TracingContext();
        threadLocal.set(tracingContext);
        return tracingContext;
    }

    public static void clear() {
        threadLocal.set((TracingContext) null);
    }

    public static void set(TracingContext tracingContext) {
        threadLocal.set(tracingContext);
    }

    public static String getTraceId() {
        return MDC.get("X-Trace-ID");
    }

    public static String getSpanId() {
        return MDC.get("X-Span-ID");
    }

    public static void log() {
        TracingContext tracingContext = get();
        if (tracingContext == null) {
            failOnNotInitialized();
        }

        boolean loggingActive = Boolean.parseBoolean(MDC.get("X-TracingContext-Active"));
        tracingContext.log(loggingActive);
        tracingContext.printAccessLog();
        tracingContext.printOutboundApiCalls();
    }

    private static void failOnNotInitialized() {
        throw new IllegalStateException("There is no TracingContext associated with the current thread. Was the TracingContext correctly initialized?");
    }

    public static void forceLogging() {
        MDC.put("X-TracingContext-Active", "true");
    }
}
