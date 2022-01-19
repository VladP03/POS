package com.pos.JWT.controller;

import com.pos.JWT.service.LoginService;
import jwt.pos.com.login.RequestLogin;
import jwt.pos.com.login.ResponseLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Log4j2
@Endpoint
@RequiredArgsConstructor
public class LoginController {

    public static final String NAMESPACE_URI = "http://com.pos.JWT/login";

    private final LoginService loginService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-Login")
    @ResponsePayload
    public ResponseLogin loginUser(@RequestPayload RequestLogin input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return loginService.loginUser(input);
    }



    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Controller %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
