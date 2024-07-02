package com.example.barbershopsystem.service;

import com.example.barbershopsystem.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 尤里尤气
 *
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
