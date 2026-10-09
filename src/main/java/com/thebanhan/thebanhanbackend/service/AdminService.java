package com.thebanhan.thebanhanbackend.service;

import com.thebanhan.thebanhanbackend.dto.AdminDtos.LoginResponse;
import com.thebanhan.thebanhanbackend.persistence.entity.AdminUser;
import com.thebanhan.thebanhanbackend.persistence.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveAdminUser(String id, String password) {
        if (id == null || id.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("ID와 Password를 입력하세요.");
        }
        adminUserRepository.save(new AdminUser(id, passwordEncoder.encode(password)));
    }

    @Transactional(readOnly = true)
    public LoginResponse login(String id, String password) {
        boolean success = adminUserRepository.findById(id)
                .map(adminUser -> passwordEncoder.matches(password, adminUser.getPassword()))
                .orElse(false);
        return new LoginResponse(success, success ? id : null);
    }
}
