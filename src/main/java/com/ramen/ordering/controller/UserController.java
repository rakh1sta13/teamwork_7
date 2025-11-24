package com.ramen.ordering.controller;

import com.ramen.ordering.dto.UserDTO;
import com.ramen.ordering.entity.UserEntity;
import com.ramen.ordering.entity.enums.UserRole;
import com.ramen.ordering.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Superadmin can create users
    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestParam String username,
                                             @RequestParam String password,
                                             @RequestParam UserRole role) {
        UserEntity user = userService.createUser(username, password, role);
        UserDTO userDTO = convertToDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserEntity user = userService.findById(id);
        UserDTO userDTO = convertToDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserEntity> users = userService.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
    @PutMapping("/{id}/password")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable Long id,
                                                  @RequestParam String newPassword) {
        UserEntity updatedUser = userService.updatePassword(id, newPassword);
        UserDTO userDTO = convertToDTO(updatedUser);
        return ResponseEntity.ok(userDTO);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping("/{id}/role")
    public ResponseEntity<UserDTO> changeUserRole(@PathVariable Long id,
                                                  @RequestParam UserRole newRole) {
        userService.changeUserRole(id, newRole);
        UserEntity updatedUser = userService.findById(id);
        UserDTO userDTO = convertToDTO(updatedUser);
        return ResponseEntity.ok(userDTO);
    }

    private UserDTO convertToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setIsActive(user.getIsActive());
        return userDTO;
    }
}