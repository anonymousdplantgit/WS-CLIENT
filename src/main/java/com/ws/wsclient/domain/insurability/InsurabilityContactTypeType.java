
package com.ws.wsclient.domain.insurability;

public enum InsurabilityContactTypeType {

    AMBULATORY_CARE("ambulatory_care"),
    HOSPITALIZED_FOR_DAY("hospitalized_for_day"),
    HOSPITALIZED_ELSEWHERE("hospitalized_elsewhere"),
    OTHER("other");
    private final String value;

    InsurabilityContactTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InsurabilityContactTypeType fromValue(String v) {
        for (InsurabilityContactTypeType c: InsurabilityContactTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
