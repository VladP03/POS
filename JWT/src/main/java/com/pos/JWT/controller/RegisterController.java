package com.pos.JWT.controller;

import com.pos.JWT.repository.Role;
import com.pos.JWT.service.RegisterService;
import com.pos.JWT.service.HeaderTokenService;
import com.pos.JWT.service.exception.ForbiddenException;
import com.pos.JWT.service.exception.TokenExpiredException;
import com.pos.JWT.service.exception.TokenInvalidException;
import com.pos.JWT.service.exception.TokenOnBlacklistException;
import com.pos.JWT.service.exception.UnauthorizedException;
import jwt.pos.com.register.RequestRegister;
import jwt.pos.com.register.ResponseRegister;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.servlet.http.HttpServletRequest;


@Log4j2
@Endpoint
@RequiredArgsConstructor
public class RegisterController {

    public static final String NAMESPACE_URI = "http://com.pos.JWT/register";

    private final RegisterService registerService;
    private final HeaderTokenService headerTokenService;
    private final HttpServletRequest request;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-Register")
    @ResponsePayload
    public ResponseRegister registerUser(@RequestPayload RequestRegister input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        final String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        headerTokenService.checkHeaderToken(headerToken);
        headerTokenService.checkIfHasAdminRole(headerToken);

        return registerService.registerUser(input);
    }


    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Controller %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
