package com.ra.service.uploadService;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

    String uploadFileToServer(MultipartFile file);
}
