package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.UserBalance;
import com.example.barbershopsystem.service.UserBalanceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 用户金额表(UserBalance)表控制层
 *
 * @author makejava
 * @since 2024-04-20 17:56:18
 */
@RestController
@RequestMapping("userBalance")
public class UserBalanceController  {
    /**
     * 服务对象
     */
    @Resource
    private UserBalanceService userBalanceService;

    @GetMapping("/getMoney/{id}")
    public ResponseResult getMoney(@PathVariable Long id){
        return userBalanceService.getMoney(id);
    }
}

