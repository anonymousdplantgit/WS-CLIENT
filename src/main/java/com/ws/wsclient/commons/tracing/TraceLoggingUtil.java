package com.ws.wsclient.commons.tracing;


import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

public class TraceLoggingUtil {
    private static final Set<String> TOKEN_HEADERS = Arrays.asList("authorization", "x-original-authorization").stream().collect(Collectors.toSet());
    private static final Set<String> TOKEN_COOKIES = Arrays.asList("KEYCLOAK_ADAPTER_STATE").stream().collect(Collectors.toSet());
    private static final String TOKEN_DELIMITER = ".";
    private static final String MASK = "[redacted]";
    private static final String COOKIE_TOKEN_SEPARATOR = "_";
    private static final Pattern COOKIE_TOKEN_SEPARATOR_SPLIT_PATTERN = Pattern.compile("_");

    public static HttpHeaders stripSignatureFromTokenHeaders(HttpHeaders headers) {
        if (headers == null) {
            return null;
        } else {
            HttpHeaders updatedHttpHeaders = new HttpHeaders();
            updatedHttpHeaders.addAll(headers);
            TOKEN_HEADERS.forEach((tokenHeader) -> {
                updatedHttpHeaders.computeIfPresent(tokenHeader, (key, headerValues) -> {
                    return stripSignatureFromTokenHeaderValues(headerValues);
                });
            });
            return updatedHttpHeaders;
        }
    }

    public static Cookie[] stripSignatureFromTokenCookies(Cookie[] cookies) {
        if (cookies == null) {
            return new Cookie[0];
        } else {
            Cookie[] updatedCookies = new Cookie[cookies.length];

            for(int i = 0; i < cookies.length; ++i) {
                if (TOKEN_COOKIES.contains(cookies[i].getName())) {
                    Cookie updatedCookie = (Cookie)cookies[i].clone();
                    updatedCookie.setValue(stripSignatureFromTokenCookieValue(cookies[i].getValue()));
                    updatedCookies[i] = updatedCookie;
                } else {
                    updatedCookies[i] = cookies[i];
                }
            }

            return updatedCookies;
        }
    }

    private static List<String> stripSignatureFromTokenHeaderValues(List<String> headerValues) {
        return (List)headerValues.stream().map(TraceLoggingUtil::stripSignature).collect(Collectors.toList());
    }

    private static String stripSignatureFromTokenCookieValue(String cookieValue) {
        return cookieValue == null ? null : (String)COOKIE_TOKEN_SEPARATOR_SPLIT_PATTERN.splitAsStream(cookieValue).map(TraceLoggingUtil::stripSignature).collect(Collectors.joining("_"));
    }

    private static String stripSignature(String value) {
        return StringUtils.countMatches(value, ".") == 2 ? StringUtils.substringBeforeLast(value, ".").concat(".").concat("[redacted]") : value;
    }

    private TraceLoggingUtil() {
    }
}
