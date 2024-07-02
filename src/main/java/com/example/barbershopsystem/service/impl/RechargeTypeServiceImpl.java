package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.Hairstyle;
import com.example.barbershopsystem.domain.RechargeType;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.RechargeTypeMapper;
import com.example.barbershopsystem.service.RechargeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (RechargeType)表服务实现类
 *
 * @author makejava
 * @since 2024-04-17 16:43:44
 */
@Service("rechargeTypeService")
public class RechargeTypeServiceImpl extends ServiceImpl<RechargeTypeMapper, RechargeType> implements RechargeTypeService {
    @Autowired
    private RechargeTypeMapper rechargeTypeMapper;

    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        Page<RechargeType> page = new Page<>(pageNum,pageSize);
        rechargeTypeMapper.selectPage(page,null);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult addInfo(RechargeType rechargeType) {
        int insert = rechargeTypeMapper.insert(rechargeType);
        if (insert<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(RechargeType rechargeType) {
        int update = rechargeTypeMapper.updateById(rechargeType);
        if (update<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        RechargeType rechargeType = rechargeTypeMapper.selectById(id);
        if (ObjectUtils.isNull(rechargeType)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(rechargeType);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = rechargeTypeMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String name) {
        LambdaQueryWrapper<RechargeType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(RechargeType::getName,name);
        Page<RechargeType> page = new Page<>(pageNum,pageSize);
        rechargeTypeMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getNames() {
        List<RechargeType> rechargeTypes = rechargeTypeMapper.selectList(null);

        if (rechargeTypes.size() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(rechargeTypes);
    }
}
