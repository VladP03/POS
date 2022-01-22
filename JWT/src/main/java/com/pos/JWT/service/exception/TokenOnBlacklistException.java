package com.pos.JWT.service.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class TokenOnBlacklistException extends RuntimeException {

    public TokenOnBlacklistException() {
        super("Token is on black list");
    }
}
