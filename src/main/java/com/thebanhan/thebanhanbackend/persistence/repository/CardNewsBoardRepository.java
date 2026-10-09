package com.thebanhan.thebanhanbackend.persistence.repository;

import com.thebanhan.thebanhanbackend.persistence.entity.CardNewsBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardNewsBoardRepository extends JpaRepository<CardNewsBoard, Long> {

    Page<CardNewsBoard> findByCategory_SeqAndVisibleTrueOrderBySortOrderAscSeqDesc(Long categorySeq, Pageable pageable);

    Page<CardNewsBoard> findByVisibleTrueOrderBySortOrderAscSeqDesc(Pageable pageable);
}
