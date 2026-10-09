package com.thebanhan.thebanhanbackend.persistence.repository;

import com.thebanhan.thebanhanbackend.persistence.entity.BoardCategory;
import com.thebanhan.thebanhanbackend.persistence.entity.BoardType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {

    List<BoardCategory> findByBoardTypeAndActiveTrueOrderBySortOrderAscSeqAsc(BoardType boardType);
}
