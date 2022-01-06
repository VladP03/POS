package com.pos.JWT.service;

import com.pos.JWT.repository.Role;
import com.pos.JWT.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.pos.JWT.repository.User.User userToLoad = getUserByUsername(username);
        List<GrantedAuthority> authorityList = getUserAuthority(userToLoad.getRole());

        return new User(userToLoad.getUsername(), userToLoad.getPassword(), authorityList);
    }


    private com.pos.JWT.repository.User.User getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }


    private List<GrantedAuthority> getUserAuthority(Role userRole) {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole.toString()));
    }
}
