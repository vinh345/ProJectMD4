package com.ra.controller;

import com.ra.model.dto.response.DataResponeSuccess;
import com.ra.service.uploadService.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api.myservice.com/v1/upload")
public class UploadController {
    @Autowired private IUploadService uploadService;

    @PostMapping
    public ResponseEntity<DataResponeSuccess> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = uploadService.uploadFileToServer(file);
        return new ResponseEntity<DataResponeSuccess>(new DataResponeSuccess(url), HttpStatus.OK);
    }
}
