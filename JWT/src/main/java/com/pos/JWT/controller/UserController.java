package com.pos.JWT.controller;

import com.pos.JWT.service.UserService;
import jwt.pos.com.user.RequestChangePassword;
import jwt.pos.com.user.RequestChangeRole;
import jwt.pos.com.user.RequestDeleteUser;
import jwt.pos.com.user.ResponseChangePassword;
import jwt.pos.com.user.ResponseChangeRole;
import jwt.pos.com.user.ResponseDeleteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.servlet.http.HttpServletRequest;

@Endpoint
@RequiredArgsConstructor
public class UserController {

    private static final String NAMESPACE_URI = "http://com.pos.JWT/User";
    private static final String TOKEN_HEADER = "Authorization";

    private final UserService userService;
    private final HttpServletRequest httpServletRequest;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-DeleteUser")
    @ResponsePayload
    public ResponseDeleteUser deleteUser(@RequestPayload RequestDeleteUser input) {
        final String token = httpServletRequest.getHeader(TOKEN_HEADER).substring(7);

        return userService.deleteUser(token, input);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-ChangeRole")
    @ResponsePayload
    public ResponseChangeRole changeRole(@RequestPayload RequestChangeRole input) {
        final String token = httpServletRequest.getHeader(TOKEN_HEADER).substring(7);

        return userService.changeRole(token, input);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-ChangePassword")
    @ResponsePayload
    public ResponseChangePassword changePassword(@RequestPayload RequestChangePassword input) {
        final String token = httpServletRequest.getHeader(TOKEN_HEADER).substring(7);

        return userService.changePassword(token, input);
    }
}
