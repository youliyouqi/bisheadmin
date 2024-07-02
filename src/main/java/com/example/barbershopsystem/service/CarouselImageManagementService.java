package com.example.barbershopsystem.service;

import com.example.barbershopsystem.domain.Carousel;
import com.example.barbershopsystem.domain.ResponseResult;

/**
 * @author 尤里尤气
 * Created on 2024/4/11-13:27
 */
public interface CarouselImageManagementService {
    ResponseResult getInfo();

    ResponseResult getInfoById(Long id);

    ResponseResult updateInfo(Carousel carousel);

    ResponseResult getImg();
}
