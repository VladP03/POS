package com.pos.JWT.controller;

import com.pos.JWT.service.AuthenticationService;
import jwt.pos.com.login.RequestLogin;
import jwt.pos.com.login.ResponseLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class LoginController {

    private static final String NAMESPACE_URI = "http://com.pos.JWT/login";

    private final AuthenticationService authenticationService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-Login")
    @ResponsePayload
    public ResponseLogin loginUser(@RequestPayload RequestLogin input) {
        return authenticationService.loginUser(input);
    }
}
