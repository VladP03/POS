package com.pos.JWT.service;

import com.pos.JWT.model.UserAdapter;
import com.pos.JWT.model.UserDTO;
import com.pos.JWT.repository.Role;
import com.pos.JWT.repository.User.User;
import com.pos.JWT.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserDTO loadUserByUsername(String username) {
        return UserAdapter.toDTO(userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Incorrect username or password")));
    }


    public void authenticate(String username, String password) {
        if (!bCryptPasswordEncoder.matches(password, getEncodedPasswordForCertainUsername(username))) {
            throw new RuntimeException("Incorrect username or password");
        }
    }


    public UserDTO createUser(String username, String password) {
        return UserAdapter.toDTO(userRepository.save(User.builder()
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .role(Role.USER)
                .build())
        );
    }


    public void checkIfUsernameExists(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
    }


    public void passwordValidation(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

        if (!password.matches(pattern)) {
            throw new RuntimeException("Try a better password");
        }
    }


    private String getEncodedPasswordForCertainUsername(String username) {
        return userRepository.getByUsername(username).getPassword();
    }
}
