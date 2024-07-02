package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.DiscountActivities;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.DiscountActivitiesMapper;
import com.example.barbershopsystem.service.DiscountActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (DiscountActivities)表服务实现类
 *
 * @author makejava
 * @since 2024-04-18 15:18:32
 */
@Service("discountActivitiesService")
public class DiscountActivitiesServiceImpl extends ServiceImpl<DiscountActivitiesMapper, DiscountActivities> implements DiscountActivitiesService {
    @Autowired
    private DiscountActivitiesMapper discountActivitiesMapper;
    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        Page<DiscountActivities> page = new Page<>(pageNum,pageSize);
        discountActivitiesMapper.selectPage(page,null);
        System.out.println(page.getRecords()+"返回的记录优惠活动");
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        DiscountActivities discountActivities = discountActivitiesMapper.selectById(id);
        if (ObjectUtils.isNull(discountActivities)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(discountActivities);
    }

    @Override
    public ResponseResult addDiscountActivities(DiscountActivities discountActivities) {
        System.out.println(discountActivities+"要存入的数据");
        int insert = discountActivitiesMapper.insert(discountActivities);
        if (insert<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(DiscountActivities discountActivities) {
        int update = discountActivitiesMapper.updateById(discountActivities);
        if (update<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String name) {
        System.out.println("优惠活动的模糊传回的字"+name);
        LambdaQueryWrapper<DiscountActivities> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(DiscountActivities::getTitle,name);
        Page<DiscountActivities> page = new Page<>(pageNum,pageSize);
        discountActivitiesMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = discountActivitiesMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
