package com.pos.JWT.service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException() {
        super("Token invalid");
    }
}
