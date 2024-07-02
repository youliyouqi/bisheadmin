package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.UserBalance;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.UserBalanceMapper;
import com.example.barbershopsystem.service.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户金额表(UserBalance)表服务实现类
 *
 * @author makejava
 * @since 2024-04-20 17:56:18
 */
@Service("userBalanceService")
public class UserBalanceServiceImpl extends ServiceImpl<UserBalanceMapper, UserBalance> implements UserBalanceService {
    @Autowired
    private UserBalanceMapper userBalanceMapper;

    @Override
    public ResponseResult getMoney(Long id) {
        LambdaQueryWrapper<UserBalance> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserBalance::getUserId,id);
        UserBalance userBalance = userBalanceMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(userBalance)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(userBalance);
    }
}
