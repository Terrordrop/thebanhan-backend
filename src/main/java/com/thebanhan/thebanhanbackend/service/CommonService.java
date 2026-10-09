package com.thebanhan.thebanhanbackend.service;

import com.thebanhan.thebanhanbackend.dto.CommonDtos.CommonFileResponse;
import com.thebanhan.thebanhanbackend.persistence.entity.CommonFile;
import com.thebanhan.thebanhanbackend.persistence.entity.CommonFileAttribute;
import com.thebanhan.thebanhanbackend.persistence.entity.FileType;
import com.thebanhan.thebanhanbackend.persistence.repository.CommonFileAttributeRepository;
import com.thebanhan.thebanhanbackend.persistence.repository.CommonFileRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {

    private final CommonFileRepository commonFileRepository;
    private final CommonFileAttributeRepository commonFileAttributeRepository;

    @Value("${app.upload-dir:/app/uploads}")
    private String uploadDir;

    @Transactional
    public CommonFile upload(FileType fileType, MultipartFile file, String attributeName, String attributeValue) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("업로드 파일이 없습니다.");
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename() == null ? "upload" : file.getOriginalFilename());
        String extension = extensionOf(originalFileName);
        String storedFileName = UUID.randomUUID() + extension;
        Path uploadPath = Path.of(uploadDir);
        Path storedPath = uploadPath.resolve(storedFileName).normalize();

        try {
            Files.createDirectories(uploadPath);
            file.transferTo(storedPath);
        } catch (IOException e) {
            throw new IllegalStateException("파일 저장에 실패했습니다.", e);
        }

        CommonFile saved = commonFileRepository.save(new CommonFile(
                fileType,
                originalFileName,
                storedFileName,
                storedPath.toString(),
                "/uploads/" + storedFileName,
                file.getContentType(),
                file.getSize()
        ));

        if (StringUtils.hasText(attributeName)) {
            commonFileAttributeRepository.save(new CommonFileAttribute(saved.getSeq(), attributeName, attributeValue));
        }

        return saved;
    }

    @Transactional(readOnly = true)
    public List<CommonFileResponse> findAllFiles() {
        return commonFileRepository.findAll()
                .stream()
                .map(CommonFileResponse::from)
                .toList();
    }

    private String extensionOf(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(dotIndex);
    }
}
