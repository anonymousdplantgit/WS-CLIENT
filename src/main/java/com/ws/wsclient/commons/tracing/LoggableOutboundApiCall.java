package com.ws.wsclient.commons.tracing;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LoggableOutboundApiCall {
    Logger OUTBOUND_API_CALLS_LOG = LoggerFactory.getLogger("OUTBOUND_API_CALLS");
    String DEFAULT_LOGGING_VALUE = "-";

    String getMethod();

    String getUrl();

    Integer getStatus();

    LocalDateTime getStartTime();

    LocalDateTime getEndTime();

    default void log(String type) {
        if (OUTBOUND_API_CALLS_LOG.isInfoEnabled()) {
            OUTBOUND_API_CALLS_LOG.info("{} {} {} {} {} {} {}", new Object[]{type,
                    this.getMethod(),
                    this.getUrl(),
                    this.getStatus(),
                    this.getStartTime(),
                    this.getEndTime(),
                    this.getStartTime() != null && this.getEndTime() != null ? this.getStartTime().until(this.getEndTime(), ChronoUnit.MILLIS) : DEFAULT_LOGGING_VALUE});
        }
    }


}
