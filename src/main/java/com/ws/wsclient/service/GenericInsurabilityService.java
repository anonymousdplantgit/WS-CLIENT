package com.ws.wsclient.service;

import be.fgov.ehealth.genericinsurability.protocol.v1.GetInsurabilityResponseType;
import com.ws.wsclient.domain.insurability.GetInsurabilityAsXmlOrFlatRequestType;

public interface GenericInsurabilityService {
     GetInsurabilityResponseType getInsurability(GetInsurabilityAsXmlOrFlatRequestType body);
}
