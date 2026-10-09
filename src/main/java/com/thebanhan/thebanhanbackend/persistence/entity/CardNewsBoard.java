package com.thebanhan.thebanhanbackend.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Entity
@Table(name = "cardNewsBoard")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardNewsBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_seq", nullable = false)
    private BoardCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_file_seq", nullable = false)
    private CommonFile imageFile;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "alt_text", nullable = false, length = 500)
    private String altText;

    @Column(name = "target_url", nullable = false, length = 1000)
    private String targetUrl;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_visible", nullable = false)
    private Boolean visible = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private AdminUser createdBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public CardNewsBoard(BoardCategory category, CommonFile imageFile, String title, String altText, String targetUrl, Integer sortOrder, Boolean visible, AdminUser createdBy) {
        this.category = category;
        this.imageFile = imageFile;
        this.title = title;
        this.altText = altText;
        this.targetUrl = targetUrl;
        this.sortOrder = sortOrder == null ? 0 : sortOrder;
        this.visible = visible == null ? true : visible;
        this.createdBy = createdBy;
    }

    public void update(BoardCategory category, CommonFile imageFile, String title, String altText, String targetUrl, Integer sortOrder, Boolean visible) {
        this.category = category;
        if (imageFile != null) {
            this.imageFile = imageFile;
        }
        this.title = title;
        this.altText = altText;
        this.targetUrl = targetUrl;
        this.sortOrder = sortOrder == null ? 0 : sortOrder;
        this.visible = visible == null ? true : visible;
    }
}
