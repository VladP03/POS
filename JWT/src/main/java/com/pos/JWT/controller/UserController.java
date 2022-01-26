package com.pos.JWT.controller;

import com.pos.JWT.jwt.JwtTokenUtil;
import com.pos.JWT.repository.Role;
import com.pos.JWT.service.HeaderTokenService;
import com.pos.JWT.service.UserService;
import com.pos.JWT.service.exception.ForbiddenException;
import com.pos.JWT.service.exception.TokenExpiredException;
import com.pos.JWT.service.exception.TokenInvalidException;
import com.pos.JWT.service.exception.TokenOnBlacklistException;
import com.pos.JWT.service.exception.UnauthorizedException;
import jwt.pos.com.changepassword.RequestChangePassword;
import jwt.pos.com.changepassword.ResponseChangePassword;
import jwt.pos.com.changerole.RequestChangeRole;
import jwt.pos.com.changerole.ResponseChangeRole;
import jwt.pos.com.deleteuser.RequestDeleteUser;
import jwt.pos.com.deleteuser.ResponseDeleteUser;
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
public class UserController {

    public static final String DELETE_NAMESPACE_URI = "http://com.pos.JWT/DeleteUser";
    public static final String ROLE_NAMESPACE_URI = "http://com.pos.JWT/ChangeRole";
    public static final String PASSWORD_NAMESPACE_URI = "http://com.pos.JWT/ChangePassword";

    private final UserService userService;
    private final HeaderTokenService headerTokenService;
    private final HttpServletRequest request;


    @PayloadRoot(namespace = DELETE_NAMESPACE_URI, localPart = "Request-DeleteUser")
    @ResponsePayload
    public ResponseDeleteUser deleteUser(@RequestPayload RequestDeleteUser input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        final String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        headerTokenService.checkHeaderToken(headerToken);
        headerTokenService.checkIfHasAdminRole(headerToken);

        return userService.deleteUser(input);
    }


    @PayloadRoot(namespace = ROLE_NAMESPACE_URI, localPart = "Request-ChangeRole")
    @ResponsePayload
    public ResponseChangeRole changeRole(@RequestPayload RequestChangeRole input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        final String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        headerTokenService.checkHeaderToken(headerToken);
        headerTokenService.checkIfHasAdminRole(headerToken);

        return userService.changeRole(input);
    }


    @PayloadRoot(namespace = PASSWORD_NAMESPACE_URI, localPart = "Request-ChangePassword")
    @ResponsePayload
    public ResponseChangePassword changePassword(@RequestPayload RequestChangePassword input) {
        createLoggerMessage(Thread.currentThread().getStackTrace()[1].getMethodName());

        final String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        headerTokenService.checkHeaderToken(headerToken);

        return userService.changePassword(input, headerToken.substring(7));
    }



    private void createLoggerMessage(String methodName) {
        final String LOGGER_TEMPLATE = "Controller %s -> calling method %s";
        final String className = this.getClass().getSimpleName();

        log.info(String.format(LOGGER_TEMPLATE, className, methodName));
    }
}
