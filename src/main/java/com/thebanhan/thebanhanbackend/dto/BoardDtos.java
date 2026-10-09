package com.thebanhan.thebanhanbackend.dto;

import com.thebanhan.thebanhanbackend.persistence.entity.BeforeAfterBoard;
import com.thebanhan.thebanhanbackend.persistence.entity.BoardCategory;
import com.thebanhan.thebanhanbackend.persistence.entity.CardNewsBoard;
import java.time.LocalDateTime;

public final class BoardDtos {

    private BoardDtos() {
    }

    public record CategoryResponse(Long seq, String code, String name) {
        public static CategoryResponse from(BoardCategory category) {
            return new CategoryResponse(category.getSeq(), category.getCategoryCode(), category.getCategoryName());
        }
    }

    public record CardNewsRequest(Long categorySeq, String title, String altText, String targetUrl, Integer sortOrder, Boolean visible, String adminId) {
    }

    public record CardNewsResponse(Long seq, Long categorySeq, String categoryName, String title, String altText, String targetUrl, String imageUrl, Integer sortOrder, Boolean visible, LocalDateTime createdAt, LocalDateTime updatedAt) {
        public static CardNewsResponse from(CardNewsBoard board) {
            return new CardNewsResponse(
                    board.getSeq(),
                    board.getCategory().getSeq(),
                    board.getCategory().getCategoryName(),
                    board.getTitle(),
                    board.getAltText(),
                    board.getTargetUrl(),
                    board.getImageFile().getFileUrl(),
                    board.getSortOrder(),
                    board.getVisible(),
                    board.getCreatedAt(),
                    board.getUpdatedAt()
            );
        }
    }

    public record BeforeAfterRequest(Long categorySeq, String title, String altText, Integer sortOrder, Boolean visible, String adminId) {
    }

    public record BeforeAfterResponse(Long seq, Long categorySeq, String categoryName, String title, String altText, String imageUrl, Integer sortOrder, Boolean visible, LocalDateTime createdAt, LocalDateTime updatedAt) {
        public static BeforeAfterResponse from(BeforeAfterBoard board) {
            return new BeforeAfterResponse(
                    board.getSeq(),
                    board.getCategory().getSeq(),
                    board.getCategory().getCategoryName(),
                    board.getTitle(),
                    board.getAltText(),
                    board.getImageFile().getFileUrl(),
                    board.getSortOrder(),
                    board.getVisible(),
                    board.getCreatedAt(),
                    board.getUpdatedAt()
            );
        }
    }
}
