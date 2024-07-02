package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.DiscountActivities;
import com.example.barbershopsystem.domain.ResponseResult;


/**
 * (DiscountActivities)表服务接口
 *
 * @author makejava
 * @since 2024-04-18 15:18:32
 */
public interface DiscountActivitiesService extends IService<DiscountActivities> {

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult getInfoById(Long id);

    ResponseResult addDiscountActivities(DiscountActivities discountActivities);

    ResponseResult update(DiscountActivities discountActivities);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String name);

    ResponseResult deleteById(Long id);
}
