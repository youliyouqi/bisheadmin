package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.UserBalance;


/**
 * 用户金额表(UserBalance)表服务接口
 *
 * @author makejava
 * @since 2024-04-20 17:56:18
 */
public interface UserBalanceService extends IService<UserBalance> {

    ResponseResult getMoney(Long id);
}
