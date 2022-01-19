package com.pos.JWT.controller;

import com.pos.JWT.service.TokenService;
import jwt.pos.com.token.RequestDestroyToken;
import jwt.pos.com.token.RequestValidateToken;
import jwt.pos.com.token.ResponseDestroyToken;
import jwt.pos.com.token.ResponseValidateToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Log4j2
@Endpoint
@RequiredArgsConstructor
public class TokenController {

    public static final String NAMESPACE_URI = "http://com.pos.JWT/Token";

    private final TokenService validateTokenService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-ValidateToken")
    @ResponsePayload
    public ResponseValidateToken validateToken(@RequestPayload RequestValidateToken input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return validateTokenService.validateToken(input.getToken());
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-DestroyToken")
    @ResponsePayload
    public ResponseDestroyToken destroyToken(@RequestPayload RequestDestroyToken input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return validateTokenService.destroyToken(input.getToken());
    }



    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Controller %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
