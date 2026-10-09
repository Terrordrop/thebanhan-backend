package com.thebanhan.thebanhanbackend.config;

import com.thebanhan.thebanhanbackend.persistence.repository.AdminUserRepository;
import com.thebanhan.thebanhanbackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@RequiredArgsConstructor
public class AdminBootstrapConfig {

    private final AdminService adminService;
    private final AdminUserRepository adminUserRepository;

    @Value("${app.admin.bootstrap-id:}")
    private String bootstrapId;

    @Value("${app.admin.bootstrap-password:}")
    private String bootstrapPassword;

    @Bean
    public CommandLineRunner adminBootstrapRunner() {
        return args -> {
            if (StringUtils.hasText(bootstrapId) && StringUtils.hasText(bootstrapPassword) && !adminUserRepository.existsById(bootstrapId)) {
                adminService.saveAdminUser(bootstrapId, bootstrapPassword);
            }
        };
    }
}
