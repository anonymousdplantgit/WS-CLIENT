
package com.ws.wsclient.commons.wsdl.xjb;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class XSLocalTimeCustomBinder {

    private XSLocalTimeCustomBinder() {
    }

    public static LocalTime parseTime(String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        try {
            return LocalTime.parse(string);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Could not parse xs:time", e);
        }
    }

    public static String printTime(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

}
