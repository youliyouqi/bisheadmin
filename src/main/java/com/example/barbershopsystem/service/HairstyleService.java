package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.Hairstyle;
import com.example.barbershopsystem.domain.ResponseResult;


/**
 * 发型表(Hairstyle)表服务接口
 *
 * @author makejava
 * @since 2024-04-17 13:04:21
 */
public interface HairstyleService extends IService<Hairstyle>{

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult getInfoById(Long id);

    ResponseResult addHairstyle(Hairstyle hairstyle);

    ResponseResult update(Hairstyle hairstyle);

    ResponseResult deleteById(Long id);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String title);

    ResponseResult getNames();


}
