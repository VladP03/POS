package com.pos.JWT.service;

import com.pos.JWT.jwt.JwtTokenUtil;
import jwt.pos.com.token.RequestDestroyToken;
import jwt.pos.com.token.RequestValidateToken;
import jwt.pos.com.token.ResponseDestroyToken;
import jwt.pos.com.token.ResponseValidateToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private static final List<String> blackList = new ArrayList<>();


    public ResponseValidateToken validateToken(String token) {

        if (blackList.contains(token)) {
            throw new RuntimeException("Token invalid");
        }

        if (!jwtTokenUtil.isValidToken(token)) {
            blackList.add(token);
            throw new RuntimeException("Token invalid");
        }

        final String username = jwtTokenUtil.getUsernameFromToken(token);
        final String role = jwtTokenUtil.getRoleFromToken(token);

        checkIfUserFromTokenExists(username);

        return setResponse(username, role);
    }

    public ResponseDestroyToken destroyToken(String token) {

        if (!jwtTokenUtil.isValidToken(token)) {
            blackList.add(token);
            throw new RuntimeException("Token invalid");
        }

        blackList.add(token);

        return setResponse();
    }

    public String getUsernameFromToken(String token) {
        return jwtTokenUtil.getUsernameFromToken(token);
    }


    private void checkIfUserFromTokenExists(String username) {
        userDetailsService.loadUserByUsername(username);
    }


    private ResponseValidateToken setResponse(String username, String role) {
        ResponseValidateToken responseValidateToken = new ResponseValidateToken();

        responseValidateToken.setSub(username);
        responseValidateToken.setRole(role);

        return responseValidateToken;
    }

    private ResponseDestroyToken setResponse() {
        ResponseDestroyToken responseDestroyToken = new ResponseDestroyToken();

        responseDestroyToken.setResult("Deleted successfully");

        return responseDestroyToken;
    }
}
