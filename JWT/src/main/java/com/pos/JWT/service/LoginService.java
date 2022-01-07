package com.pos.JWT.service;

import com.pos.JWT.jwt.JwtTokenUtil;
import com.pos.JWT.model.UserDTO;
import jwt.pos.com.login.RequestLogin;
import jwt.pos.com.login.ResponseLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;


    public ResponseLogin loginUser(@RequestPayload RequestLogin input) {
        UserDTO userDTO = userDetailsService.loadUserByUsername(input.getUsername());

        userDetailsService.authenticate(input.getUsername(), input.getPassword());

        final String jwtToken = jwtTokenUtil.generateToken(userDTO);

        ResponseLogin token = new ResponseLogin();
        token.setToken(jwtToken);

        return token;
    }
}
