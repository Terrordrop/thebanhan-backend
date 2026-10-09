package com.thebanhan.thebanhanbackend.service;

import com.thebanhan.thebanhanbackend.dto.ApiPageResponse;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.BeforeAfterRequest;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.BeforeAfterResponse;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.CardNewsRequest;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.CardNewsResponse;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.CategoryResponse;
import com.thebanhan.thebanhanbackend.persistence.entity.AdminUser;
import com.thebanhan.thebanhanbackend.persistence.entity.BeforeAfterBoard;
import com.thebanhan.thebanhanbackend.persistence.entity.BoardCategory;
import com.thebanhan.thebanhanbackend.persistence.entity.BoardType;
import com.thebanhan.thebanhanbackend.persistence.entity.CardNewsBoard;
import com.thebanhan.thebanhanbackend.persistence.entity.CommonFile;
import com.thebanhan.thebanhanbackend.persistence.repository.AdminUserRepository;
import com.thebanhan.thebanhanbackend.persistence.repository.BeforeAfterBoardRepository;
import com.thebanhan.thebanhanbackend.persistence.repository.BoardCategoryRepository;
import com.thebanhan.thebanhanbackend.persistence.repository.CardNewsBoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContentsService {

    private final BoardCategoryRepository boardCategoryRepository;
    private final CardNewsBoardRepository cardNewsBoardRepository;
    private final BeforeAfterBoardRepository beforeAfterBoardRepository;
    private final AdminUserRepository adminUserRepository;
    private final CommonService commonService;

    @Transactional(readOnly = true)
    public List<CategoryResponse> findCategories(BoardType boardType) {
        return boardCategoryRepository.findByBoardTypeAndActiveTrueOrderBySortOrderAscSeqAsc(boardType)
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    @Transactional
    public CardNewsResponse createCardNews(CardNewsRequest request, CommonFile imageFile) {
        BoardCategory category = findCategory(request.categorySeq(), BoardType.CARD_NEWS);
        AdminUser adminUser = findAdminUser(request.adminId());
        CardNewsBoard saved = cardNewsBoardRepository.save(new CardNewsBoard(
                category,
                imageFile,
                request.title(),
                request.altText(),
                request.targetUrl(),
                request.sortOrder(),
                request.visible(),
                adminUser
        ));
        return CardNewsResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public ApiPageResponse<CardNewsResponse> findCardNews(Long categorySeq, int page, int size) {
        PageRequest pageRequest = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100));
        Page<CardNewsResponse> result = categorySeq == null
                ? cardNewsBoardRepository.findByVisibleTrueOrderBySortOrderAscSeqDesc(pageRequest).map(CardNewsResponse::from)
                : cardNewsBoardRepository.findByCategory_SeqAndVisibleTrueOrderBySortOrderAscSeqDesc(categorySeq, pageRequest).map(CardNewsResponse::from);
        return ApiPageResponse.from(result);
    }

    @Transactional
    public BeforeAfterResponse createBeforeAfter(BeforeAfterRequest request, CommonFile imageFile) {
        BoardCategory category = findCategory(request.categorySeq(), BoardType.BEFORE_AFTER);
        AdminUser adminUser = findAdminUser(request.adminId());
        BeforeAfterBoard saved = beforeAfterBoardRepository.save(new BeforeAfterBoard(
                category,
                imageFile,
                request.title(),
                request.altText(),
                request.sortOrder(),
                request.visible(),
                adminUser
        ));
        return BeforeAfterResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public ApiPageResponse<BeforeAfterResponse> findBeforeAfter(Long categorySeq, int page, int size) {
        PageRequest pageRequest = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100));
        Page<BeforeAfterResponse> result = categorySeq == null
                ? beforeAfterBoardRepository.findByVisibleTrueOrderBySortOrderAscSeqDesc(pageRequest).map(BeforeAfterResponse::from)
                : beforeAfterBoardRepository.findByCategory_SeqAndVisibleTrueOrderBySortOrderAscSeqDesc(categorySeq, pageRequest).map(BeforeAfterResponse::from);
        return ApiPageResponse.from(result);
    }

    public CommonService commonService() {
        return commonService;
    }

    private BoardCategory findCategory(Long categorySeq, BoardType boardType) {
        return boardCategoryRepository.findById(categorySeq)
                .filter(category -> category.getBoardType() == boardType)
                .orElseThrow(() -> new IllegalArgumentException("게시판 카테고리를 찾을 수 없습니다."));
    }

    private AdminUser findAdminUser(String adminId) {
        if (adminId == null || adminId.isBlank()) {
            return null;
        }
        return adminUserRepository.findById(adminId).orElse(null);
    }
}
