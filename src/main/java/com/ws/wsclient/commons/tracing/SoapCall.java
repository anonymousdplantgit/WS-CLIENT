package com.ws.wsclient.commons.tracing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class SoapCall implements LoggableOutboundApiCall, Serializable {
    private String request;
    private String response;
    private String exception;
    private String uri;
    private Integer status;
    private String operation;
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    private LocalDateTime startTime;
    @JsonSerialize(
            using = LocalDateTimeSerializer.class
    )
    @JsonDeserialize(
            using = LocalDateTimeDeserializer.class
    )
    private LocalDateTime endTime;

    public SoapCall() {
    }

    public String getRequest() {
        return this.request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getException() {
        return this.exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getMethod() {
        return this.getOperation();
    }

    public String getUrl() {
        return this.getUri();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            SoapCall soapCall = (SoapCall)o;
            return Objects.equals(this.request, soapCall.request) && Objects.equals(this.response, soapCall.response) && Objects.equals(this.exception, soapCall.exception) && Objects.equals(this.uri, soapCall.uri) && Objects.equals(this.status, soapCall.status) && Objects.equals(this.operation, soapCall.operation) && Objects.equals(this.startTime, soapCall.startTime) && Objects.equals(this.endTime, soapCall.endTime);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.request, this.response, this.exception, this.uri, this.status, this.operation, this.startTime, this.endTime});
    }

    public String toString() {
        return "SoapCall{request='" + this.request + "', response='" + this.response + "', exception='" + this.exception + "', uri='" + this.uri + "', status=" + this.status + ", operation='" + this.operation + "', startTime=" + this.startTime + ", endTime=" + this.endTime + "}";
    }
}
