
package com.ws.wsclient.domain.insurability;

public enum InsurabilityRequestTypeType {

    INFORMATION("information"),
    INVOICING("invoicing");
    private final String value;

    InsurabilityRequestTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InsurabilityRequestTypeType fromValue(String v) {
        for (InsurabilityRequestTypeType c: InsurabilityRequestTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
