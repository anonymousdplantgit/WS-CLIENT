
package com.ws.wsclient.domain.sts;

import lombok.*;
import org.w3._2000._09.xmldsig_.SignatureType;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class ResponseAbstractType {
    protected SignatureType signature;
    protected String responseID;
    protected String inResponseTo;
    protected BigInteger majorVersion;
    protected BigInteger minorVersion;
    protected XMLGregorianCalendar issueInstant;
    protected String recipient;

}
