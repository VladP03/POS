package com.pos.JWT.controller;

import com.pos.JWT.service.UserService;
import jwt.pos.com.user.RequestChangePassword;
import jwt.pos.com.user.RequestChangeRole;
import jwt.pos.com.user.RequestDeleteUser;
import jwt.pos.com.user.ResponseChangePassword;
import jwt.pos.com.user.ResponseChangeRole;
import jwt.pos.com.user.ResponseDeleteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
@RequiredArgsConstructor
public class UserController {

    private static final String NAMESPACE_URI = "http://com.pos.JWT/User";

    private final UserService userService;


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-DeleteUser")
    @ResponsePayload
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseDeleteUser deleteUser(@RequestPayload RequestDeleteUser input) {
        return userService.deleteUser(input);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-ChangeRole")
    @ResponsePayload
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseChangeRole changeRole(@RequestPayload RequestChangeRole input) {
        return userService.changeRole(input);
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "Request-ChangePassword")
    @ResponsePayload
    public ResponseChangePassword changePassword(@RequestPayload RequestChangePassword input) {
        return userService.changePassword(input);
    }
}
