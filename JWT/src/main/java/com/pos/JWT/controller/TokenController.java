package com.pos.JWT.controller;

import com.pos.JWT.service.TokenService;
import jwt.pos.com.token.RequestDestroyToken;
import jwt.pos.com.token.RequestValidateToken;
import jwt.pos.com.token.ResponseDestroyToken;
import jwt.pos.com.token.ResponseValidateToken;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
@RequiredArgsConstructor
public class TokenController {

    private static final String NAMESPACE_URI = "http://com.pos.JWT/Token";

    private final TokenService validateTokenService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-ValidateToken")
    @ResponsePayload
    public ResponseValidateToken validateToken(@RequestPayload RequestValidateToken input) {
        return validateTokenService.validateToken(input.getToken());
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-DestroyToken")
    @ResponsePayload
    public ResponseDestroyToken destroyToken(@RequestPayload RequestDestroyToken input) {
        return validateTokenService.destroyToken(input.getToken());
    }
}
