package com.thebanhan.thebanhanbackend.controller;

import com.thebanhan.thebanhanbackend.dto.BoardDtos.BeforeAfterRequest;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.BeforeAfterResponse;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.CardNewsRequest;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.CardNewsResponse;
import com.thebanhan.thebanhanbackend.dto.BoardDtos.CategoryResponse;
import com.thebanhan.thebanhanbackend.dto.CommonDtos.CommonFileResponse;
import com.thebanhan.thebanhanbackend.persistence.entity.BoardType;
import com.thebanhan.thebanhanbackend.persistence.entity.CommonFile;
import com.thebanhan.thebanhanbackend.persistence.entity.FileType;
import com.thebanhan.thebanhanbackend.service.AdminService;
import com.thebanhan.thebanhanbackend.service.CommonService;
import com.thebanhan.thebanhanbackend.service.ContentsService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final CommonService commonService;
    private final ContentsService contentsService;

    @GetMapping("/me")
    public String me(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/users")
    public void saveAdminUser(@RequestParam String id, @RequestParam String password) {
        adminService.saveAdminUser(id, password);
    }

    @GetMapping("/categories")
    public List<CategoryResponse> categories(@RequestParam BoardType boardType) {
        return contentsService.findCategories(boardType);
    }

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonFileResponse uploadFile(
            @RequestParam FileType fileType,
            @RequestParam MultipartFile file,
            @RequestParam(required = false) String attributeName,
            @RequestParam(required = false) String attributeValue
    ) {
        return CommonFileResponse.from(commonService.upload(fileType, file, attributeName, attributeValue));
    }

    @GetMapping("/files")
    public List<CommonFileResponse> files() {
        return commonService.findAllFiles();
    }

    @PostMapping(value = "/card-news", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CardNewsResponse createCardNews(
            @RequestParam Long categorySeq,
            @RequestParam String title,
            @RequestParam String altText,
            @RequestParam String targetUrl,
            @RequestParam(defaultValue = "0") Integer sortOrder,
            @RequestParam(defaultValue = "true") Boolean visible,
            @RequestParam MultipartFile image,
            Principal principal
    ) {
        CommonFile imageFile = commonService.upload(FileType.IMAGE, image, "BOARD_TYPE", "CARD_NEWS");
        return contentsService.createCardNews(new CardNewsRequest(categorySeq, title, altText, targetUrl, sortOrder, visible, principal.getName()), imageFile);
    }

    @PostMapping(value = "/before-after", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BeforeAfterResponse createBeforeAfter(
            @RequestParam Long categorySeq,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String altText,
            @RequestParam(defaultValue = "0") Integer sortOrder,
            @RequestParam(defaultValue = "true") Boolean visible,
            @RequestParam MultipartFile image,
            Principal principal
    ) {
        CommonFile imageFile = commonService.upload(FileType.IMAGE, image, "BOARD_TYPE", "BEFORE_AFTER");
        return contentsService.createBeforeAfter(new BeforeAfterRequest(categorySeq, title, altText, sortOrder, visible, principal.getName()), imageFile);
    }
}
