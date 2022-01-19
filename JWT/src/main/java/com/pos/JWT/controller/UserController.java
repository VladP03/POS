package com.pos.JWT.controller;

import com.pos.JWT.service.UserService;
import jwt.pos.com.user.RequestChangePassword;
import jwt.pos.com.user.RequestChangeRole;
import jwt.pos.com.user.RequestDeleteUser;
import jwt.pos.com.user.ResponseChangePassword;
import jwt.pos.com.user.ResponseChangeRole;
import jwt.pos.com.user.ResponseDeleteUser;
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
public class UserController {

    public static final String NAMESPACE_URI = "http://com.pos.JWT/User";

    private final UserService userService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-DeleteUser")
    @ResponsePayload
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseDeleteUser deleteUser(@RequestPayload RequestDeleteUser input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return userService.deleteUser(input);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-ChangeRole")
    @ResponsePayload
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseChangeRole changeRole(@RequestPayload RequestChangeRole input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return userService.changeRole(input);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-ChangePassword")
    @ResponsePayload
    public ResponseChangePassword changePassword(@RequestPayload RequestChangePassword input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        return userService.changePassword(input);
    }



    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Controller %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
