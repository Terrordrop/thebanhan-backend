package com.thebanhan.thebanhanbackend.dto;

import java.util.List;
import org.springframework.data.domain.Page;

public record ApiPageResponse<T>(
        List<T> items,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static <T> ApiPageResponse<T> from(Page<T> page) {
        return new ApiPageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
