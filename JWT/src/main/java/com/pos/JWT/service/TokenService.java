package com.pos.JWT.service;

import com.pos.JWT.jwt.JwtTokenUtil;
import com.pos.JWT.repository.Role;
import com.pos.JWT.service.exception.TokenOnBlacklistException;
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


    public ResponseValidateToken validateToken(String tokenToCheck) {
        checkTokenValidity(tokenToCheck);
        checkIfTokenIsOnBlacklist(tokenToCheck);

        final String username = jwtTokenUtil.getSubject(tokenToCheck);
        final Role role = jwtTokenUtil.getSubjectRole(tokenToCheck);

        checkIfUserFromTokenExists(username);

        return setResponse(username, role);
    }

    public ResponseDestroyToken destroyToken(String tokenToDestroy) {
        blackList.add(tokenToDestroy);

        return setResponse();
    }



    private void checkIfUserFromTokenExists(String username) {
        userDetailsService.loadUserByUsername(username);
    }


    private void checkTokenValidity(String token) {
        jwtTokenUtil.validateToken(token);
    }

    private void checkIfTokenIsOnBlacklist(String token) {
        if (blackList.contains(token)) {
            throw new TokenOnBlacklistException();
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
