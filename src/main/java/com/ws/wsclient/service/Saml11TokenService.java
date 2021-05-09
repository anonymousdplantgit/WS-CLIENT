package com.ws.wsclient.service;

import com.ws.wsclient.domain.sts.RequestType;
import com.ws.wsclient.domain.sts.ResponseType;

public interface Saml11TokenService {
    public ResponseType requestSecurityToken(RequestType body);
}
