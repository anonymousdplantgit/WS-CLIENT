package com.ws.wsclient.commons.tracing;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

@JsonInclude(Include.NON_NULL)
public class TracingContext implements Serializable {
    private static final Logger TRACING_CONTEXT_LOG = LoggerFactory.getLogger("TRACING_CONTEXT");
    private static final Logger ACCESS_LOG = LoggerFactory.getLogger("ACCESS");
    @JsonIgnore
    private final ObjectMapper objectMapper;
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    private final LocalDateTime initTime;
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    private LocalDateTime endTime;
    private double duration;
    @JsonIgnore
    private boolean printedToLog;
    @JsonIgnore
    private HttpHeaders httpHeaders;
    @JsonProperty("httpHeaders")
    private HttpHeaders loggableHttpHeaders;
    private String uri;
    private String methodName;
    @JsonIgnore
    private Cookie[] cookies;
    @JsonProperty("cookies")
    private Cookie[] loggableCookies;
    private String queryString;
    private String protocol;
    private final List<SoapCall> soapCalls;
    private String requestBody;
    private String responseBody;
    private Integer httpStatus;
    private String queueName;
    private Map<String, String> authorizationDetail;

    public TracingContext() {
        this.soapCalls = new ArrayList();
        this.initTime = LocalDateTime.now();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public TracingContext(HttpServletRequest serverRequest) {
        this();
        HttpHeaders headers = this.getHttpHeaders(serverRequest);
        this.cookies = serverRequest.getCookies();
        this.httpHeaders = headers;
        this.uri = serverRequest.getRequestURI();
        this.methodName = serverRequest.getMethod();
        this.queryString = serverRequest.getQueryString();
        this.protocol = serverRequest.getProtocol();
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Integer getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }



    public List<SoapCall> getSoapCalls() {
        return this.soapCalls;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isPrintedToLog() {
        return this.printedToLog;
    }

    public void setPrintedToLog(boolean printedToLog) {
        this.printedToLog = printedToLog;
    }

    public HttpHeaders getHttpHeaders() {
        return this.httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Cookie[] getCookies() {
        return this.cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public Cookie[] getLoggableCookies() {
        return this.loggableCookies;
    }

    public void setLoggableCookies(Cookie[] loggableCookies) {
        this.loggableCookies = loggableCookies;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Map<String, String> getAuthorizationDetail() {
        return this.authorizationDetail;
    }

    public void setAuthorizationDetail(Map<String, String> authorizationDetail) {
        this.authorizationDetail = authorizationDetail;
    }

    public HttpHeaders getLoggableHttpHeaders() {
        return this.loggableHttpHeaders;
    }

    public void setLoggableHttpHeaders(HttpHeaders loggableHttpHeaders) {
        this.loggableHttpHeaders = loggableHttpHeaders;
    }



    public void log(boolean alwaysLog) {
        if (!this.printedToLog) {
                this.printedToLog = true;
                String valueAsString = this.asString();
                    TRACING_CONTEXT_LOG.info(valueAsString);
                }

    }

    public String asString() {
        if (this.endTime == null) {
            this.endTime = LocalDateTime.now();
        }

        this.duration = (double)this.initTime.until(this.endTime, ChronoUnit.MILLIS) / 1000.0D;
        this.setLoggableHttpHeaders(TraceLoggingUtil.stripSignatureFromTokenHeaders(this.httpHeaders));
        this.setLoggableCookies(TraceLoggingUtil.stripSignatureFromTokenCookies(this.cookies));
        if (this.getLoggableHttpHeaders() != null) {
            this.getLoggableHttpHeaders().remove("Cookie");
        }

        try {
            return this.objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException var2) {
            TRACING_CONTEXT_LOG.error("JSON Error:", var2);
            return null;
        }
    }

    public void printAccessLog() {
        if (this.endTime == null) {
            this.endTime = LocalDateTime.now();
        }

        long durationMillis = this.initTime.until(this.endTime, ChronoUnit.MILLIS);
        if (this.queueName != null) {
            ACCESS_LOG.info("\"QUEUE {}\" {}", this.queueName, durationMillis);
        } else if (!"/healthz".equals(this.uri)) {
            String request = this.queryString != null ? this.uri + "?" + this.queryString : this.uri;
            ACCESS_LOG.info("\"{} {} {}\" {} {}", new Object[]{this.methodName, request, this.protocol, this.httpStatus, durationMillis});
        }

    }

    public void printOutboundApiCalls() {
        this.printOutboundApiCalls("SOAP", this.soapCalls);
    }

    private void printOutboundApiCalls(String type, List<? extends LoggableOutboundApiCall> outboundApiCalls) {
        outboundApiCalls.forEach((call) -> {
            call.log(type);
        });
    }

    private HttpHeaders getHttpHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration names = request.getHeaderNames();

        while(names.hasMoreElements()) {
            String name = (String)names.nextElement();
            Enumeration values = request.getHeaders(name);

            while(values.hasMoreElements()) {
                String value = (String)values.nextElement();
                headers.add(name, value);
            }
        }

        return headers;
    }
}
