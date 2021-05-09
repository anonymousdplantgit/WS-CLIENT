package com.ws.wsclient.service.impl;

import com.ws.wsclient.adapter.Saml11TokenServiceAdapter;
import com.ws.wsclient.domain.sts.RequestType;
import com.ws.wsclient.domain.sts.ResponseType;
import com.ws.wsclient.service.Saml11TokenService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Saml11TokenServiceImpl implements Saml11TokenService {
    Saml11TokenServiceAdapter adapter;
    DozerBeanMapper dozerBeanMapper;

    @Autowired
    public Saml11TokenServiceImpl(Saml11TokenServiceAdapter adapter, DozerBeanMapper dozerBeanMapper) {
        this.adapter = adapter;
        this.dozerBeanMapper = dozerBeanMapper;
    }


    public ResponseType requestSecurityToken(RequestType body) {
        return dozerBeanMapper.map(
                adapter.requestSecurityToken(dozerBeanMapper.map(body, oasis.names.tc.saml._1_0.protocol.RequestType.class)),
                ResponseType.class);
    }
}
