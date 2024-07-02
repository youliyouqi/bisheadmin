package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.Hairstyle;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.Systemintro;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.HairstyleMapper;
import com.example.barbershopsystem.mapper.SystemintroMapper;
import com.example.barbershopsystem.service.HairstyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;

/**
 * 发型表(Hairstyle)表服务实现类
 *
 * @author makejava
 * @since 2024-04-17 13:04:21
 */
@Service("hairstyleService")
public  class HairstyleServiceImpl extends ServiceImpl<HairstyleMapper, Hairstyle>implements HairstyleService {
    @Autowired
    private HairstyleMapper hairstyleMapper;

    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        Page<Hairstyle> page = new Page<>(pageNum,pageSize);
        hairstyleMapper.selectPage(page,null);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        Hairstyle hairstyle = hairstyleMapper.selectById(id);
        if (ObjectUtils.isNull(hairstyle)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(hairstyle);
    }

    @Override
    public ResponseResult addHairstyle(Hairstyle hairstyle) {
        System.out.println(hairstyle+"传入的发型");
        int insert = hairstyleMapper.insert(hairstyle);
        if (insert<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(Hairstyle hairstyle) {
        int update = hairstyleMapper.updateById(hairstyle);
        if (update<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = hairstyleMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String title) {
        LambdaQueryWrapper<Hairstyle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Hairstyle::getName,title);
        Page<Hairstyle> page = new Page<>(pageNum,pageSize);
        hairstyleMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getNames() {
        List<Hairstyle> hairstyles = hairstyleMapper.selectList(null);
        if (hairstyles.size() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(hairstyles);
    }




}
