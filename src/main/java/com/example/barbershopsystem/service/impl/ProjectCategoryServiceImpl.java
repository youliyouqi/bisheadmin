package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.ProjectCategory;
import com.example.barbershopsystem.domain.RechargeType;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.ProjectCategoryMapper;
import com.example.barbershopsystem.service.ProjectCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (ProjectCategory)表服务实现类
 *
 * @author makejava
 * @since 2024-04-17 17:55:26
 */
@Service("projectCategoryService")
public class ProjectCategoryServiceImpl extends ServiceImpl<ProjectCategoryMapper, ProjectCategory> implements ProjectCategoryService {

     @Autowired
     private ProjectCategoryMapper projectCategoryMapper;
    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        Page<ProjectCategory> page = new Page<>(pageNum,pageSize);
        projectCategoryMapper.selectPage(page,null);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult addInfo(ProjectCategory projectCategory) {
        int insert = projectCategoryMapper.insert(projectCategory);
        if (insert<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(ProjectCategory projectCategory) {
        int update = projectCategoryMapper.updateById(projectCategory);
        if (update<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        ProjectCategory projectCategory = projectCategoryMapper.selectById(id);
        if (ObjectUtils.isNull(projectCategory)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(projectCategory);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = projectCategoryMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String name) {
        LambdaQueryWrapper<ProjectCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(ProjectCategory::getName,name);
        Page<ProjectCategory> page = new Page<>(pageNum,pageSize);
        projectCategoryMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getNames() {
        List<ProjectCategory> projectCategories = projectCategoryMapper.selectList(null);
        if (projectCategories.size() == 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(projectCategories);
    }
}
