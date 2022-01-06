package com.pos.JWT.controller;

import com.pos.JWT.service.ValidateTokenService;
import jwt.pos.com.validatetoken.RequestValidateToken;
import jwt.pos.com.validatetoken.ResponseValidateToken;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ValidateTokenController {

    private static final String NAMESPACE_URI = "http://com.pos.JWT/validateToken";

    private final ValidateTokenService validateTokenService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-ValidateToken")
    @ResponsePayload
    public ResponseValidateToken validateToken(@RequestPayload RequestValidateToken input) {
        return validateTokenService.validateToken(input);
    }

}
