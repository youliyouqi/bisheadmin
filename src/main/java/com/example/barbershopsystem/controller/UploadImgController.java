package com.example.barbershopsystem.controller;

import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 尤里尤气
 *
 */
@RestController
public class UploadImgController {
    @Autowired
    private UploadService uploadService;
    @PostMapping("/uploadImg")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
