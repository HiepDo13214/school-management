package com.school.school_management.service;

import com.school.school_management.entity.RefreshTokens;
import com.school.school_management.entity.Roles;
import com.school.school_management.entity.Schools;
import com.school.school_management.entity.Users;
import com.school.school_management.repository.RefreshTokensRepository;
import com.school.school_management.repository.RolesRepository;
import com.school.school_management.repository.SchoolsRepository;
import com.school.school_management.repository.UsersRepository;
import com.school.school_management.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private SchoolsRepository schoolsRepository;

    @Autowired
    private RefreshTokensRepository refreshTokensRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ Register
    public Users register(String email, String password, String fullName, Integer roleId, Integer schoolId) throws Exception {
        if (usersRepository.findByEmail(email).isPresent()) {
            throw new Exception("Email already exists");
        }

        Users user = new Users();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setCreatedAt(new Date());

        Roles role = rolesRepository.findById(roleId)
                .orElseThrow(() -> new Exception("Role not found"));
        user.setRoleId(role);

        Schools school = schoolsRepository.findById(schoolId)
                .orElseThrow(() -> new Exception("School not found"));
        user.setSchoolId(school);

        return usersRepository.save(user);
    }

    // ✅ Login -> trả JWT
    public String login(String email, String password) throws Exception {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new Exception("Invalid password");
        }

        user.setLastLoginAt(new Date());
        usersRepository.save(user);

        return jwtUtils.generateJwtToken(user.getEmail(), user.getRoleId().getName());
    }

    // ✅ Forgot password -> tạo token và TRẢ VỀ để FE/Postman dùng
    public String forgotPassword(String email) throws Exception {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        String token = UUID.randomUUID().toString();
        user.setPasswordResetToken(token);
        user.setPasswordResetExpiresAt(new Date(System.currentTimeMillis() + 3600_000)); // 1 tiếng

        usersRepository.save(user);

        // 👉 Đây chính là token bạn sẽ thấy trong response hoặc NetBeans console
        System.out.println("📩 Reset token cho " + email + ": " + token);
        return token;
    }

    // ✅ Reset password
    public void resetPassword(String email, String token, String newPassword) throws Exception {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        if (user.getPasswordResetToken() == null || !user.getPasswordResetToken().equals(token)) {
            throw new Exception("Invalid token");
        }

        if (user.getPasswordResetExpiresAt().before(new Date())) {
            throw new Exception("Token expired");
        }

        // 👉 Đặt lại password
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setPasswordResetToken(null);
        user.setPasswordResetExpiresAt(null);
        usersRepository.save(user);

        // ✅ Sau khi đổi pass thành công -> lưu refresh token mới
        RefreshTokens refreshToken = new RefreshTokens();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(new Date());
        refreshToken.setExpiresAt(new Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000))); // 30 ngày
        refreshToken.setRevoked(false);
        refreshToken.setUserId(user);

        refreshTokensRepository.save(refreshToken);

        System.out.println("✅ Password reset thành công. Refresh token mới: " + refreshToken.getToken());
    }
}
