package com.pos.JWT.service;

import com.pos.JWT.jwt.JwtTokenUtil;
import jwt.pos.com.validatetoken.RequestValidateToken;
import jwt.pos.com.validatetoken.ResponseValidateToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateTokenService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;


    public ResponseValidateToken validateToken(RequestValidateToken input) {
        ResponseValidateToken responseValidateToken = new ResponseValidateToken();

        final String token = input.getToken();
        final String username = jwtTokenUtil.getUsernameFromToken(token);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        responseValidateToken.setResult(jwtTokenUtil.validateToken(token, userDetails));

        return responseValidateToken;
    }
}
