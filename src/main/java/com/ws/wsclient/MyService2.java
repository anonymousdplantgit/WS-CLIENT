package com.ws.wsclient;

import be.fgov.ehealth.genericinsurability.protocol.v1.*;
import org.springframework.stereotype.Service;

@Service
public class MyService2  implements GenericInsurabilityPortType {

    @Override
    public GetInsurabilityAsFlatResponseType getInsurabilityAsFlat(GetInsurabilityAsXmlOrFlatRequestType body) throws SystemError {
        return new GenericInsurabilityService().getGenericInsurabilityServiceSOAP11(null).getInsurabilityAsFlat(null);
    }

    @Override
    public GetInsurabilityResponseType getInsurability(GetInsurabilityAsXmlOrFlatRequestType body) throws SystemError {
        return new GenericInsurabilityService().getGenericInsurabilityServiceSOAP11(null).getInsurability(body);
    }

}
