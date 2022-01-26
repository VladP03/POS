package com.pos.JWT.service;

import com.pos.JWT.jwt.JwtTokenUtil;
import com.pos.JWT.repository.Role;
import com.pos.JWT.service.exception.ForbiddenException;
import com.pos.JWT.service.exception.TokenExpiredException;
import com.pos.JWT.service.exception.TokenInvalidException;
import com.pos.JWT.service.exception.TokenOnBlacklistException;
import com.pos.JWT.service.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeaderTokenService {

    private final JwtTokenUtil jwtTokenUtil;


    public void checkHeaderToken(String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnauthorizedException();
        }

        try {
            jwtTokenUtil.validateToken(token.substring(7));
        } catch (TokenExpiredException | TokenInvalidException | TokenOnBlacklistException exception) {
            throw new UnauthorizedException();
        }
    }


    public void checkIfHasAdminRole(String token) {
        final Role subRole = jwtTokenUtil.getSubjectRole(token.substring(7));

         if (subRole != Role.ADMIN) {
             throw new ForbiddenException();
         }
    }
}
