
package com.ws.wsclient.domain.sts;

import lombok.*;
import org.w3._2000._09.xmldsig_.SignatureType;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class RequestAbstractType {

    protected List<QName> respondWith;
    protected SignatureType signature;
    protected String requestID;
    protected BigInteger majorVersion;
    protected BigInteger minorVersion;
    protected XMLGregorianCalendar issueInstant;

}
