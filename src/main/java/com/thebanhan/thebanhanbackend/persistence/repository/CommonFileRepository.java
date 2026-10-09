package com.thebanhan.thebanhanbackend.persistence.repository;

import com.thebanhan.thebanhanbackend.persistence.entity.CommonFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonFileRepository extends JpaRepository<CommonFile, Long> {
}
