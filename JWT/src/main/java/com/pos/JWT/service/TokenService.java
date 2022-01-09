package com.pos.JWT.service;

import com.pos.JWT.jwt.JwtTokenUtil;
import com.pos.JWT.repository.Role;
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


    public ResponseValidateToken validateToken(String headerToken, String tokenToCheck) {
        checkHeaderTokenIsValid(headerToken);

        checkIfTokenIsOnBlacklist(tokenToCheck);
        checkTokenIsValid(tokenToCheck);

        final String username = jwtTokenUtil.getUsernameFromToken(tokenToCheck);
        final Role role = jwtTokenUtil.getRoleFromToken(tokenToCheck);

        checkIfUserFromTokenExists(username);

        return setResponse(username, role);
    }

    public ResponseDestroyToken destroyToken(String headerToken, String tokenToDestroy) {
        checkHeaderTokenIsValid(headerToken);
        checkHasAdminRole(headerToken);

        blackList.add(tokenToDestroy);

        return setResponse();
    }



    public String getUsernameFromToken(String token) {
        return jwtTokenUtil.getUsernameFromToken(token);
    }


    private void checkIfUserFromTokenExists(String username) {
        userDetailsService.loadUserByUsername(username);
    }



    private void checkHeaderTokenIsValid(String headerToken) {
        if (!jwtTokenUtil.isValidToken(headerToken)) {
            throw new RuntimeException("Invalid HeaderToken");
        }
    }

    private void checkTokenIsValid(String token) {
        if (!jwtTokenUtil.isValidToken(token)) {
            throw new RuntimeException("Invalid token");
        }
    }

    private void checkHasAdminRole(String token) {
        if (!jwtTokenUtil.getRoleFromToken(token).equals(Role.ADMIN)) {
            throw new RuntimeException("FORBIDDEN");
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
