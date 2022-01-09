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

import javax.servlet.http.HttpServletRequest;

@Endpoint
@RequiredArgsConstructor
public class TokenController {

    private static final String NAMESPACE_URI = "http://com.pos.JWT/Token";
    private static final String TOKEN_HEADER = "Authorization";

    private final TokenService validateTokenService;
    private final HttpServletRequest httpServletRequest;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-ValidateToken")
    @ResponsePayload
    public ResponseValidateToken validateToken(@RequestPayload RequestValidateToken input) {
        final String headerToken = httpServletRequest.getHeader(TOKEN_HEADER).substring(7);
        checkIfTokenIsPresent(headerToken);

        return validateTokenService.validateToken(headerToken, input.getToken());
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-DestroyToken")
    @ResponsePayload
    public ResponseDestroyToken validateToken(@RequestPayload RequestDestroyToken input) {
        final String headerToken = httpServletRequest.getHeader(TOKEN_HEADER).substring(7);
        checkIfTokenIsPresent(headerToken);

        return validateTokenService.destroyToken(headerToken, input.getToken());
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
