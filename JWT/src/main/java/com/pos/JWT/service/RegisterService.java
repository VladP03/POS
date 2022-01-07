package com.pos.JWT.service;


import com.pos.JWT.model.UserDTO;
import jwt.pos.com.register.RequestRegister;
import jwt.pos.com.register.ResponseRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserDetailsService userDetailsService;
    private final ValidateTokenService validateTokenService;

    public ResponseRegister registerUser(@RequestPayload RequestRegister input) {

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
