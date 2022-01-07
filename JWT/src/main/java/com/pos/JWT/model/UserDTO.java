package com.pos.JWT.model;

import com.pos.JWT.repository.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private Role role;
}
