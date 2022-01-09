package com.pos.JWT.controller;

import com.pos.JWT.service.RegisterService;
import jwt.pos.com.register.RequestRegister;
import jwt.pos.com.register.ResponseRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.servlet.http.HttpServletRequest;

@Endpoint
@RequiredArgsConstructor
public class RegisterController {

    private static final String NAMESPACE_URI = "http://com.pos.JWT/register";
    private static final String TOKEN_HEADER = "Authorization";

    private final RegisterService registerService;
    private final HttpServletRequest httpServletRequest;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-Register")
    @ResponsePayload
    public ResponseRegister registerUser(@RequestPayload RequestRegister input) {
        final String token = httpServletRequest.getHeader(TOKEN_HEADER).substring(7);
        checkIfTokenIsPresent(token);

        return registerService.registerUser(token, input);
    }



    private void checkIfTokenIsPresent(String token) {
        try {
            if (token.isEmpty()) {
                throw new RuntimeException("Empty token");
            }
        } catch (NullPointerException exception) {
            throw new RuntimeException("Null token");
        }
    }
}
