package com.pos.JWT.service;

import com.pos.JWT.repository.Role;
import jwt.pos.com.token.ResponseValidateToken;
import jwt.pos.com.user.RequestChangePassword;
import jwt.pos.com.user.RequestChangeRole;
import jwt.pos.com.user.RequestDeleteUser;
import jwt.pos.com.user.ResponseChangePassword;
import jwt.pos.com.user.ResponseChangeRole;
import jwt.pos.com.user.ResponseDeleteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    public ResponseDeleteUser deleteUser(String token, RequestDeleteUser input) {
        ResponseValidateToken responseValidateToken = tokenService.validateToken(token);

        if (responseValidateToken.getRole().equals(Role.ADMIN.name())) {
            userDetailsService.checkIfUserExists(input.getUsername());

            userDetailsService.deleteUser(input.getUsername());

            return setResponseDeleteUser();
        } else {
            throw new RuntimeException("FORBIDDEN");
        }
    }

    public ResponseChangeRole changeRole(String token, RequestChangeRole input) {
        ResponseValidateToken responseValidateToken = tokenService.validateToken(token);

        if (responseValidateToken.getRole().equals(Role.ADMIN.name())) {
            userDetailsService.checkIfUserExists(input.getUsername());

            userDetailsService.changeRole(input.getUsername(), transformStringToRole(input.getNewRole()));

            return setResponseChangeRole();
        } else {
            throw new RuntimeException("FORBIDDEN");
        }
    }

    public ResponseChangePassword changePassword(String token, RequestChangePassword input) {
        ResponseValidateToken responseValidateToken = tokenService.validateToken(token);

        String username = tokenService.getUsernameFromToken(token);

        userDetailsService.changePassword(username, input.getNewPassword());

        return setResponseChangePassword();
    }


    private Role transformStringToRole(String option) {
        switch (option.toUpperCase(Locale.ROOT)) {
            case "USER":
                return Role.USER;
            case "ADMIN":
                return Role.ADMIN;
            default:
                return Role.USER;
        }
    }


    private ResponseDeleteUser setResponseDeleteUser() {
        ResponseDeleteUser responseDeleteUser = new ResponseDeleteUser();

        responseDeleteUser.setResult("Deleted Successfully");

        return responseDeleteUser;
    }

    private ResponseChangeRole setResponseChangeRole() {
        ResponseChangeRole responseChangeRole = new ResponseChangeRole();

        responseChangeRole.setResult("Changed Successfully");

        return responseChangeRole;
    }

    private ResponseChangePassword setResponseChangePassword() {
        ResponseChangePassword responseChangePassword = new ResponseChangePassword();

        responseChangePassword.setResult("Password changed Successfully");

        return responseChangePassword;
    }
}
