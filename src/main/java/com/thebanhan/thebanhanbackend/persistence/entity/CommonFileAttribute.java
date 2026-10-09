package com.thebanhan.thebanhanbackend.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "commonFileAttribute")
@IdClass(CommonFileAttributeId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonFileAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Id
    @Column(name = "common_file_seq", nullable = false)
    private Long commonFileSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "common_file_seq", nullable = false, insertable = false, updatable = false)
    private CommonFile commonFile;

    @Column(name = "attribute_name", nullable = false, length = 100)
    private String attributeName;

    @Column(name = "attribute_value", length = 1000)
    private String attributeValue;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public CommonFileAttribute(Long commonFileSeq, String attributeName, String attributeValue) {
        this.commonFileSeq = commonFileSeq;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }
}
