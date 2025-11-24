package com.ramen.ordering.config;

import com.ramen.ordering.entity.enums.UserRole;

import java.util.UUID;

public record UserPrincipal(
        Long userId,
        UserRole role
) {
}
