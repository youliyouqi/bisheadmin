package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.Reviews;


/**
 * (Reviews)表服务接口
 *
 * @author makejava
 * @since 2024-04-24 21:01:23
 */
public interface ReviewsService extends IService<Reviews> {

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult getInfoById(Long id);

    ResponseResult score(Reviews reviews);

    ResponseResult getInfoListByUserId(int pageNum, int pageSize, String userId);

    ResponseResult getInfoListByBarberId(int pageNum, int pageSize, String barberId);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String name);

    ResponseResult getInfoById1(Long id);

    ResponseResult deleteById(Long id);

    ResponseResult getBonus(Long id);
}
