package com.pos.JWT.controller;

import com.pos.JWT.service.RegisterService;
import jwt.pos.com.register.RequestRegister;
import jwt.pos.com.register.ResponseRegister;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Log4j2
@Endpoint
@RequiredArgsConstructor
public class RegisterController {

    public static final String NAMESPACE_URI = "http://com.pos.JWT/register";

    private final RegisterService registerService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-Register")
    @ResponsePayload
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseRegister registerUser(@RequestPayload RequestRegister input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return registerService.registerUser(input);
    }



    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Controller %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
