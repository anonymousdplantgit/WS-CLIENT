package com.ws.wsclient.service.impl;

import be.fgov.ehealth.genericinsurability.protocol.v1.GetInsurabilityResponseType;
import be.fgov.ehealth.genericinsurability.protocol.v1.SystemError;
import com.ws.wsclient.adapter.GenericInsurabilityServiceAdapter;
import com.ws.wsclient.domain.insurability.GetInsurabilityAsXmlOrFlatRequestType;
import com.ws.wsclient.service.GenericInsurabilityService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenericInsurabilityServiceImpl implements GenericInsurabilityService {
    GenericInsurabilityServiceAdapter adapter;
    DozerBeanMapper dozerBeanMapper;
    @Autowired
    public GenericInsurabilityServiceImpl(GenericInsurabilityServiceAdapter adapter, DozerBeanMapper dozerBeanMapper) {
        this.adapter = adapter;
        this.dozerBeanMapper = dozerBeanMapper;
    }

    @Override
    public GetInsurabilityResponseType getInsurability(GetInsurabilityAsXmlOrFlatRequestType body) {
        be.fgov.ehealth.genericinsurability.protocol.v1.GetInsurabilityAsXmlOrFlatRequestType request =
                dozerBeanMapper.map(
                        body,
                        be.fgov.ehealth.genericinsurability.protocol.v1.GetInsurabilityAsXmlOrFlatRequestType.class);
        GetInsurabilityResponseType result = null;
        try {
            result = adapter.getInsurability(request);
        } catch (SystemError systemError) {
        }
        return result;
    }
}
