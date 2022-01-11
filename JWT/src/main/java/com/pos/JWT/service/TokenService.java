package com.pos.JWT.service;

import com.pos.JWT.jwt.JwtTokenUtil;
import com.pos.JWT.repository.Role;
import jwt.pos.com.token.ResponseDestroyToken;
import jwt.pos.com.token.ResponseValidateToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final List<String> blackList = new ArrayList<>();
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    public ResponseValidateToken validateToken(String tokenToCheck) {
        checkTokenIsValid(tokenToCheck);
        checkIfTokenIsOnBlacklist(tokenToCheck);

        final String username = jwtTokenUtil.getUsernameFromToken(tokenToCheck);
        final Role role = jwtTokenUtil.getRoleFromToken(tokenToCheck);

        checkIfUserFromTokenExists(username);

        return setResponse(username, role);
    }

    public ResponseDestroyToken destroyToken(String tokenToDestroy) {
        blackList.add(tokenToDestroy);

        return setResponse();
    }


    public String getUsernameFromToken(String token) {
        return jwtTokenUtil.getUsernameFromToken(token);
    }


    private void checkIfUserFromTokenExists(String username) {
        userDetailsService.loadUserByUsername(username);
    }


    private void checkTokenIsValid(String token) {
        if (!jwtTokenUtil.isValidToken(token)) {
            throw new RuntimeException("Invalid token");
        }
    }

    private void checkIfTokenIsOnBlacklist(String token) {
        if (blackList.contains(token)) {
            throw new RuntimeException("Token invalid");
        }
    }



    private ResponseValidateToken setResponse(String username, Role role) {
        ResponseValidateToken responseValidateToken = new ResponseValidateToken();

        responseValidateToken.setSub(username);
        responseValidateToken.setRole(role.toString());

        return responseValidateToken;
    }

    private ResponseDestroyToken setResponse() {
        ResponseDestroyToken responseDestroyToken = new ResponseDestroyToken();

        responseDestroyToken.setResult("Deleted successfully");

        return responseDestroyToken;
    }
}
