package com.ws.wsclient.commons.wsdl.xjb;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Custom binder for xs:dateTime - OffsetDateTime.
 *
 */
public final class XSDateTimeCustomBinder {

    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXXXX");

    //If the time part (after 'T') contains Z or z or + or - => we have a timezone
    private static Pattern dateWithTimeZonePattern = Pattern.compile(".*T.*[zZ+-].*");

    /**
     * private constructor for utility class.
     */
    private XSDateTimeCustomBinder() {
    }

    /**
     * Converts xs:dateTime formatted string to an OffsetDateTime
     *
     * @param dateString String
     * @return the OffsetDateTime
     */
    public static OffsetDateTime parseDateTime(String dateString) {
        if (dateWithTimeZonePattern.matcher(dateString).matches()) {
            return OffsetDateTime.parse(dateString);
        } else {
            return LocalDateTime.parse(dateString)
                    .atZone(ZoneId.systemDefault())
                    .toOffsetDateTime()
                    .withOffsetSameInstant(ZoneOffset.UTC);
        }
    }

    /**
     * Converts an OffsetDateTime to an xs:dateTime string representation.
     *
     * @param dateTime the date time
     * @return a string representation of the {@link OffsetDateTime} respecting the xs:dateTime format
     */
    public static String printDateTime(OffsetDateTime dateTime) {
        return dateTime.format(OUTPUT_FORMATTER);
    }
}
