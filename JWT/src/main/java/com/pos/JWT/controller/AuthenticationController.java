package com.pos.JWT.controller;

import com.pos.JWT.jwt.JwtTokenUtil;
import com.pos.JWT.jwt.JwtUserDetailsService;
import jwt.pos.com.authentication.JwtRequest;
import jwt.pos.com.authentication.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class AuthenticationController {

    private static final String NAMESPACE_URI = "http://com.pos.JWT/authentication";
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "JwtRequest")
    @ResponsePayload
    public JwtResponse login(@RequestPayload JwtRequest input) {
        try {
            authenticate(input.getUsername(), input.getPassword());
        } catch (Exception e) {

        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(input.getUsername());

        final String jwtToken = jwtTokenUtil.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(jwtToken);
        return jwtResponse;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "helloWorld")
    @ResponsePayload
    public String helloWorld() {
        return "hello World";
    }
}
