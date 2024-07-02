package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.RechargeType;
import com.example.barbershopsystem.domain.ResponseResult;


/**
 * (RechargeType)表服务接口
 *
 * @author makejava
 * @since 2024-04-17 16:43:44
 */
public interface RechargeTypeService extends IService<RechargeType> {

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult addInfo(RechargeType rechargeType);

    ResponseResult update(RechargeType rechargeType);

    ResponseResult getInfoById(Long id);

    ResponseResult deleteById(Long id);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String name);

    ResponseResult getNames();
}
