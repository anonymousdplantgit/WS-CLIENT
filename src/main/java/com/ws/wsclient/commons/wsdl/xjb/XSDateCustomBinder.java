package com.ws.wsclient.commons.wsdl.xjb;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Custom binder for xs:dateTime - Calendar.
 *
 */
public final class XSDateCustomBinder {

    /**
     * private constructor for utility class.
     */
    private XSDateCustomBinder() {
    }

    /**
     * Converts String to LocalDate
     *
     * @param string String
     * @return LocalDate
     */
    public static LocalDate parseDate(String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        try {
            return LocalDate.parse(string);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Could not parse xs:date or xs:DateTime", e);
        }
    }

    /**
     * Converts LocalDate to String
     *
     * @param localDate LocalDate
     * @return String
     */
    public static String printDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

}
