package com.ramen.ordering.service.impl;

import com.ramen.ordering.entity.UserEntity;
import com.ramen.ordering.entity.enums.UserRole;
import com.ramen.ordering.repository.UserRepository;
import com.ramen.ordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity createUser(String username, String password, UserRole role) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Hash the password
        user.setRole(role);
        user.setIsActive(true);
        return userRepository.save(user);
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity updatePassword(Long id, String newPassword) {
        UserEntity user = findById(id);
        user.setPassword(passwordEncoder.encode(newPassword)); // Hash the new password
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = findById(id);
        user.setIsActive(false); // Soft delete by setting isActive to false
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void changeUserRole(Long userId, UserRole newRole) {
        UserEntity user = findById(userId);
        user.setRole(newRole);
        userRepository.save(user);
    }
}