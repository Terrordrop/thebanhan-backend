package com.thebanhan.thebanhanbackend.controller;

import com.thebanhan.thebanhanbackend.dto.ApiPageResponse;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.BeforeAfterResponse;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.CardNewsResponse;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.CategoryResponse;
import com.thebanhan.thebanhanbackend.persistence.entity.BoardType;
import com.thebanhan.thebanhanbackend.service.ContentsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/front")
public class FrontController {

    private final ContentsService contentsService;

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }

    @GetMapping("/categories")
    public List<CategoryResponse> categories(@RequestParam BoardType boardType) {
        return contentsService.findCategories(boardType);
    }

    @GetMapping("/card-news")
    public ApiPageResponse<CardNewsResponse> cardNews(
            @RequestParam(required = false) Long categorySeq,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return contentsService.findCardNews(categorySeq, page, size);
    }

    @GetMapping("/before-after")
    public ApiPageResponse<BeforeAfterResponse> beforeAfter(
            @RequestParam(required = false) Long categorySeq,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size
    ) {
        return contentsService.findBeforeAfter(categorySeq, page, size);
    }
}
