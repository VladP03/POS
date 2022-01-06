package com.pos.JWT.service;

import com.pos.JWT.jwt.JwtTokenUtil;
import com.pos.JWT.repository.Role;
import com.pos.JWT.repository.User.User;
import com.pos.JWT.repository.User.UserRepository;
import jwt.pos.com.login.RequestLogin;
import jwt.pos.com.login.ResponseLogin;
import jwt.pos.com.register.RequestRegister;
import jwt.pos.com.register.ResponseRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;


    public ResponseRegister registerUser(@RequestPayload RequestRegister input) {
        ResponseRegister registerUser = new ResponseRegister();

        User userCreated = userRepository.save(User.builder()
                .username(input.getUsername())
                .password(passwordEncoder.encode(input.getPassword()))
                .role(Role.USER)
                .build());

        registerUser.setUsername(userCreated.getUsername());
        registerUser.setResultType("CREATED");

        return registerUser;
    }


    public ResponseLogin loginUser(@RequestPayload RequestLogin input) {
        try {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());

            authenticationManager.authenticate(auth);
        } catch (BadCredentialsException exception) {
            throw new RuntimeException("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(input.getUsername());

        final String jwtToken = jwtTokenUtil.generateToken(userDetails);
        ResponseLogin token = new ResponseLogin();
        token.setToken(jwtToken);

        return token;
    }
}
