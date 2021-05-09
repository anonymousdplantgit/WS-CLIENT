package com.ws.wsclient.commons.wsdl.xjb;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Custom binder for xs:decimal - BigDecimal.
 *
 */
public final class XSDecimalCustomBinder {

    private static final int DEFAULT_SCALE = 4;

    /**
     * private constructor for utility class.
     */
    private XSDecimalCustomBinder() {
    }

    /**
     * Converts String to BigDecimal
     *
     * @param string String
     * @return BigDecimal
     */
    public static BigDecimal parseBigDecimal(String string) {
        return new BigDecimal(string.trim(), MathContext.DECIMAL128);
    }

    /**
     * Converts BigDecimal to String
     *
     * @param bd BigDecimal
     * @return String
     */
    public static String printBigDecimal(BigDecimal bd) {
        BigDecimal rounded = bd.setScale(DEFAULT_SCALE, RoundingMode.HALF_EVEN).stripTrailingZeros();
        return rounded.signum() != 0 ? rounded.toPlainString() : "0";
    }
}
