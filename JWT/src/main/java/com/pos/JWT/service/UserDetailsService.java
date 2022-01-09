package com.pos.JWT.service;

import com.pos.JWT.model.UserAdapter;
import com.pos.JWT.model.UserDTO;
import com.pos.JWT.repository.Role;
import com.pos.JWT.repository.User.User;
import com.pos.JWT.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
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

    public void changeRole(String username, Role role) {
        User user = userRepository.getByUsername(username);
        user.setRole(role);
        userRepository.save(user);
    }

    public void changePassword(String username, String password) {
        User user = userRepository.getByUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
    }

    public void deleteUser(String username) {
        userRepository.delete(userRepository.getByUsername(username));
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


    public void checkIfUserExists(String username) {
        if (userRepository.findByUsername(username).isEmpty()) {
            throw new RuntimeException("User does not exists");
        }
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


    @Bean(name = "bCryptPasswordEncoder")
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
