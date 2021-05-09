
package com.ws.wsclient.domain.insurability;

import lombok.*;

import javax.xml.datatype.XMLGregorianCalendar;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeriodType {
    protected XMLGregorianCalendar periodStart;
    protected XMLGregorianCalendar periodEnd;

}
