package com.pos.JWT.controller;

import com.pos.JWT.service.AuthenticationService;
import jwt.pos.com.register.RequestRegister;
import jwt.pos.com.register.ResponseRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class RegisterController {

    private static final String NAMESPACE_URI = "http://com.pos.JWT/register";

    private final AuthenticationService authenticationService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-Register")
    @ResponsePayload
    public ResponseRegister registerUser(@RequestPayload RequestRegister input) {
        return authenticationService.registerUser(input);
    }
}
