package com.ramen.ordering.controller;

import com.ramen.ordering.config.JwtTokenUtil;
import com.ramen.ordering.dto.UserDTO;
import com.ramen.ordering.entity.UserEntity;
import com.ramen.ordering.entity.enums.UserRole;
import com.ramen.ordering.repository.UserRepository;
import com.ramen.ordering.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.expression.ExpressionException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        try {
            // Authenticate user
            UserEntity user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ExpressionException("User not found"));
            final String token = jwtTokenUtil.generateToken(user);

            // Create UserDTO to avoid exposing password
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setRole(user.getRole());
            userDTO.setIsActive(user.getIsActive());

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("user", userDTO);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/register")
    @Hidden
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        UserEntity user = userService.createUser(request.getUsername(), request.getPassword(), UserRole.ADMIN);

        // Create UserDTO to avoid exposing password
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setIsActive(user.getIsActive());

        return ResponseEntity.ok(userDTO);
    }

    // Inner request classes
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class RegisterRequest {
        private String username;
        private String password;

        // Getters and setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}