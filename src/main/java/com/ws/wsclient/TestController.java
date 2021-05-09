package com.ws.wsclient;


import com.ws.wsclient.domain.insurability.*;
import com.ws.wsclient.domain.sts.RequestType;
import com.ws.wsclient.domain.sts.ResponseType;
import com.ws.wsclient.service.GenericInsurabilityService;
import com.ws.wsclient.service.Saml11TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class TestController {
    @Autowired
    GenericInsurabilityService service;

    @Autowired
    Saml11TokenService saml11TokenService;

    @GetMapping(value = "/getInsurability")
    public void getInsurability() {
        GetInsurabilityAsXmlOrFlatRequestType request = GetInsurabilityAsXmlOrFlatRequestType.builder()
                .request(SingleInsurabilityRequestType.builder()
                        .insurabilityRequestDetail(InsurabilityRequestDetailType.builder()
                                .insurabilityContactType(InsurabilityContactTypeType.HOSPITALIZED_FOR_DAY)
                                .insurabilityRequestType(InsurabilityRequestTypeType.INFORMATION)
                                .build())
                        .careReceiverId(CareReceiverIdType.builder()
                                .inss("1234")
                                .mutuality("1234")
                                .regNrWithMut("521")
                                .build())
                        .build())
                .recordCommonInput(RecordCommonInputType.builder()
                        .inputReference(BigDecimal.ONE)
                        .build())
                .commonInput(CommonInputType.builder()
                        .origin(OriginType.builder()
                                .build())
                        .build())
                .build();
//        service.getInsurability(request);
//
        saml11TokenService.requestSecurityToken(new RequestType());
    }


    @GetMapping(value = "/requestSecurityToken")
    public ResponseType requestSecurityToken() {
      return  saml11TokenService.requestSecurityToken(new RequestType());
    }

}
