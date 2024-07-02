package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.Appointments;
import com.example.barbershopsystem.domain.Orders;
import com.example.barbershopsystem.domain.ResponseResult;


/**
 * (Orders)表服务接口
 *
 * @author makejava
 * @since 2024-04-24 16:30:25
 */
public interface OrdersService extends IService<Orders> {

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult approval(Orders orders);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String name);

    ResponseResult getAInfoById(Long id);

    ResponseResult getInfoListByUserId(int pageNum, int pageSize, String userId);

    ResponseResult getBaberStatus(Long id);

    ResponseResult deleteById(Long id);

    ResponseResult getInfoListByBarberId(int pageNum, int pageSize, String barberId);
}
