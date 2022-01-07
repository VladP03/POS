package com.pos.JWT.model;

import com.pos.JWT.repository.User.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserAdapter {

    public static UserDTO toDTO (User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
