package com.ramen.ordering.service;

import com.ramen.ordering.entity.UserEntity;
import com.ramen.ordering.entity.enums.UserRole;

import java.util.List;

public interface UserService {
    UserEntity createUser(String username, String password, UserRole role);
    UserEntity findById(Long id);
    List<UserEntity> findAll();
    UserEntity updatePassword(Long id, String newPassword);
    void deleteUser(Long id);
    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);
    void changeUserRole(Long userId, UserRole newRole);
}