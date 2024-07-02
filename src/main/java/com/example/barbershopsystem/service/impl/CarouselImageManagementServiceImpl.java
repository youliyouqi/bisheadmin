package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.barbershopsystem.domain.Carousel;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.CarouselImageMapper;
import com.example.barbershopsystem.service.CarouselImageManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 尤里尤气
 * Created on 2024/4/11-13:27
 */
@Service
public class CarouselImageManagementServiceImpl implements CarouselImageManagementService {
    @Autowired
    private CarouselImageMapper carouselImageMapper;
    @Override
    public ResponseResult getInfo() {
        LambdaQueryWrapper<Carousel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Carousel::getDelFlag,0);
        List<Carousel> carousels = carouselImageMapper.selectList(lambdaQueryWrapper);
        if (carousels.isEmpty()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FINDIMG_ERROR);
        }


        return ResponseResult.okResult(carousels);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        Carousel carousel = carouselImageMapper.selectById(id);
        return ResponseResult.okResult(carousel);
    }

    @Override
    public ResponseResult updateInfo(Carousel carousel) {
        int i = carouselImageMapper.updateById(carousel);
        if (i<=0) {return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getImg() {
        LambdaQueryWrapper<Carousel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Carousel::getDelFlag,0);
        List<Carousel> carousels = carouselImageMapper.selectList(lambdaQueryWrapper);
        List<String> imgUrls = new ArrayList<>();
        for (Carousel carousel : carousels) {
            imgUrls.add(carousel.getValue());
        }
        System.out.println(imgUrls+"轮播图路径");
        return ResponseResult.okResult(imgUrls);
    }
}
