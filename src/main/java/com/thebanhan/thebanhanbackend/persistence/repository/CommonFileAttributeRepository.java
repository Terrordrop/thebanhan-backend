package com.thebanhan.thebanhanbackend.persistence.repository;

import com.thebanhan.thebanhanbackend.persistence.entity.CommonFileAttribute;
import com.thebanhan.thebanhanbackend.persistence.entity.CommonFileAttributeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonFileAttributeRepository extends JpaRepository<CommonFileAttribute, CommonFileAttributeId> {
}
