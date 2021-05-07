package com.ws.wsclient;


import be.fgov.ehealth.genericinsurability.protocol.v1.SystemError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @Autowired
    MyService2 service;

    @GetMapping(value = "/test")
    public void read() throws SystemError {
        service.getInsurability(null);
    }

}
