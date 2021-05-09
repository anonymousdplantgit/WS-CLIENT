
package com.ws.wsclient.domain.insurability;

import lombok.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor @Builder
public class PaymentType {

    protected boolean paymentByIo;
    protected List<XMLGregorianCalendar> maxInvoiced;
    protected Boolean specialSocialCategory;
}
