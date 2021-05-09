package com.ws.wsclient.adapter;

import be.fgov.ehealth.genericinsurability.protocol.v1.GetInsurabilityAsXmlOrFlatRequestType;
import be.fgov.ehealth.genericinsurability.protocol.v1.GetInsurabilityResponseType;
import be.fgov.ehealth.genericinsurability.protocol.v1.SystemError;

public interface GenericInsurabilityServiceAdapter {
    public GetInsurabilityResponseType getInsurability(GetInsurabilityAsXmlOrFlatRequestType body) throws SystemError;
}
