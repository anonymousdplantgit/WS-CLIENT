package com.ws.wsclient.adapter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

public interface Saml11TokenServiceAdapter {
    public oasis.names.tc.saml._1_0.protocol.ResponseType requestSecurityToken(oasis.names.tc.saml._1_0.protocol.RequestType body);
}
