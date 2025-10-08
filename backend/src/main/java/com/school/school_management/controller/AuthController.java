package com.school.school_management.controller;

import com.school.school_management.dto.UserDTO;
import com.school.school_management.entity.Users;
import com.school.school_management.payload.*;
import com.school.school_management.repository.UsersRepository;
import com.school.school_management.service.AuthService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

     @Autowired
    private UsersRepository usersRepository;

    // Register
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) throws Exception {
        Users user = authService.register(
                request.getEmail(),
                request.getPassword(),
                request.getFullName(),
                request.getRoleId(),
                request.getSchoolId()
        );
        return ResponseEntity.ok(mapToDTO(user));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok().body("{\"token\":\"" + token + "\"}");
    }

    // Forgot password
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) throws Exception {
        String token = authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok().body("{\"resetToken\":\"" + token + "\"}");
    }

    // ✅ API xác thực token reset password
    @PostMapping("/verify-token")
    public ResponseEntity<?> verifyToken(@RequestBody VerifyTokenRequest request) {
        Users user = usersRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Email không tồn tại");
        }

        if (user.getPasswordResetToken() == null
                || !user.getPasswordResetToken().trim().equals(request.getToken().trim())) {
            return ResponseEntity.badRequest().body("Token không đúng");
        }

        if (user.getPasswordResetExpiresAt() == null
                || user.getPasswordResetExpiresAt().before(new Date())) {
            return ResponseEntity.badRequest().body("Token đã hết hạn");
        }

        return ResponseEntity.ok("Token hợp lệ");
    }

    // Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) throws Exception {
        authService.resetPassword(request.getEmail(), request.getToken(), request.getNewPassword());
        return ResponseEntity.ok().body("{\"message\":\"Password reset successful\"}");
    }

    // Map entity -> DTO
    private UserDTO mapToDTO(Users user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setRoleId(user.getRoleId().getId());
        dto.setRoleName(user.getRoleId().getName());
        dto.setSchoolId(user.getSchoolId().getId());
        dto.setSchoolName(user.getSchoolId().getName());
        return dto;
    }
}
