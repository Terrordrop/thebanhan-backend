package com.thebanhan.thebanhanbackend.dto;

import com.thebanhan.thebanhanbackend.persistence.entity.CommonFile;
import com.thebanhan.thebanhanbackend.persistence.entity.FileType;
import java.time.LocalDateTime;

public final class CommonDtos {

    private CommonDtos() {
    }

    public record CommonFileResponse(Long seq, FileType fileType, String originalFileName, String fileUrl, String contentType, Long fileSize, LocalDateTime createdAt) {
        public static CommonFileResponse from(CommonFile file) {
            return new CommonFileResponse(
                    file.getSeq(),
                    file.getFileType(),
                    file.getOriginalFileName(),
                    file.getFileUrl(),
                    file.getContentType(),
                    file.getFileSize(),
                    file.getCreatedAt()
            );
        }
    }
}
