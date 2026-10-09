package com.thebanhan.thebanhanbackend.persistence.repository;

import com.thebanhan.thebanhanbackend.persistence.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
}
