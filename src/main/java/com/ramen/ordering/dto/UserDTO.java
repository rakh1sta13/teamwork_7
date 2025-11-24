package com.ramen.ordering.dto;

import com.ramen.ordering.entity.UserEntity;
import com.ramen.ordering.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private UserRole role;
    private Boolean isActive;
}