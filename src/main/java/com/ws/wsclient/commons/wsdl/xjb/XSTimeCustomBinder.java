package com.ws.wsclient.commons.wsdl.xjb;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class XSTimeCustomBinder {

    private XSTimeCustomBinder() {
    }

    public static OffsetTime parseTime(String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        try {
            return OffsetTime.parse(string);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Could not parse xs:time", e);
        }
    }

    public static String printTime(OffsetTime offsetTime) {
        return offsetTime.format(DateTimeFormatter.ISO_OFFSET_TIME);
    }

}
