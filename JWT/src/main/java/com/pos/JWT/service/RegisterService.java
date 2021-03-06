package com.pos.JWT.service;


import com.pos.JWT.jwt.JwtTokenUtil;
import com.pos.JWT.model.UserDTO;
import com.pos.JWT.repository.Role;
import jwt.pos.com.register.RequestRegister;
import jwt.pos.com.register.ResponseRegister;
import jwt.pos.com.token.ResponseValidateToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final JwtTokenUtil jwtTokenUtil;


    public ResponseRegister registerUser(RequestRegister input) {
        final String username = input.getUsername();
        final String password = input.getPassword();

        userDetailsService.checkIfUsernameExists(username);
        //        userDetailsService.passwordValidation(password);

        UserDTO userDtoCreated = userDetailsService.createUser(username, password);

        return createResponse(userDtoCreated.getUsername());
    }



    private ResponseRegister createResponse(String username) {
        ResponseRegister response = new ResponseRegister();

        response.setUsername(username);
        response.setResultType("CREATED");

        return response;
    }
}
