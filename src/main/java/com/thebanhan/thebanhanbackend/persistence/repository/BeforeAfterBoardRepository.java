package com.thebanhan.thebanhanbackend.persistence.repository;

import com.thebanhan.thebanhanbackend.persistence.entity.BeforeAfterBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeforeAfterBoardRepository extends JpaRepository<BeforeAfterBoard, Long> {

    Page<BeforeAfterBoard> findByCategory_SeqAndVisibleTrueOrderBySortOrderAscSeqDesc(Long categorySeq, Pageable pageable);

    Page<BeforeAfterBoard> findByVisibleTrueOrderBySortOrderAscSeqDesc(Pageable pageable);
}
