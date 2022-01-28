package com.pos.JWT.service;

import com.pos.JWT.jwt.JwtTokenUtil;
import com.pos.JWT.repository.Role;
import jwt.pos.com.changepassword.RequestChangePassword;
import jwt.pos.com.changepassword.ResponseChangePassword;
import jwt.pos.com.changerole.RequestChangeRole;
import jwt.pos.com.changerole.ResponseChangeRole;
import jwt.pos.com.deleteuser.RequestDeleteUser;
import jwt.pos.com.deleteuser.ResponseDeleteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public ResponseDeleteUser deleteUser(RequestDeleteUser input) {
        userDetailsService.checkIfUserExists(input.getUsername());
        userDetailsService.deleteUser(input.getUsername());

        return setResponseDeleteUser();
    }

    public ResponseChangeRole changeRole(RequestChangeRole input) {
        userDetailsService.checkIfUserExists(input.getUsername());
        userDetailsService.changeRole(input.getUsername(), transformStringToRole(input.getNewRole()));

        return setResponseChangeRole();
    }

    public ResponseChangePassword changePassword(RequestChangePassword input, String token) {
        final String username = jwtTokenUtil.getSubject(token);

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
