package com.pos.JWT.service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("Forbidden");
    }
}
