package com.thebanhan.thebanhanbackend.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Entity
@Table(name = "adminuserTable")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminUser {

    @Id
    @Column(name = "ID", nullable = false, length = 100)
    private String id;

    @Column(name = "Password", nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public AdminUser(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
