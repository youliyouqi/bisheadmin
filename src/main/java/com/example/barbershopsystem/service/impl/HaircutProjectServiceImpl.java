package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.HaircutProject;
import com.example.barbershopsystem.domain.Hairstyle;
import com.example.barbershopsystem.domain.ProjectCategory;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.HaircutProjectMapper;
import com.example.barbershopsystem.mapper.HairstyleMapper;
import com.example.barbershopsystem.mapper.ProjectCategoryMapper;
import com.example.barbershopsystem.service.HaircutProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 理发项目管理表(HaircutProject)表服务实现类
 *
 * @author makejava
 * @since 2024-04-17 20:22:13
 */
@Service("haircutProjectService")
public class HaircutProjectServiceImpl extends ServiceImpl<HaircutProjectMapper, HaircutProject> implements HaircutProjectService {

   @Autowired
   private HaircutProjectMapper haircutProjectMapper;
   @Autowired
   private HairstyleMapper hairstyleMapper;
   @Autowired
   private ProjectCategoryMapper projectCategoryMapper;
    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        Page<HaircutProject> page = new Page<>(pageNum,pageSize);
        haircutProjectMapper.selectPage(page,null);

        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult addInfo(HaircutProject haircutProject) {
        ProjectCategory projectCategory = projectCategoryMapper.selectById(haircutProject.getCategoryId());
        haircutProject.setCategory(projectCategory.getName());
        Hairstyle hairstyle = hairstyleMapper.selectById(haircutProject.getHairstyleId());
        haircutProject.setHairstyle(hairstyle.getName());
        System.out.println(haircutProject+"准备新增的数据");
        int insert = haircutProjectMapper.insert(haircutProject);
        if (insert<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult update(HaircutProject haircutProject) {
        System.out.println(haircutProject+"传入的理发项目");
        int update = haircutProjectMapper.updateById(haircutProject);
        if (update<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        HaircutProject haircutProject = haircutProjectMapper.selectById(id);

        if (ObjectUtils.isNull(haircutProject)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(haircutProject);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = haircutProjectMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String name) {
        System.out.println("发型展示-传回的数据"+name);
        LambdaQueryWrapper<HaircutProject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(HaircutProject::getName,name);
        Page<HaircutProject> page = new Page<>(pageNum,pageSize);
        haircutProjectMapper.selectPage(page,lambdaQueryWrapper);
        System.out.println("发型展示-搜索出的数据"+page.getRecords());
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoByCategoryId(int pageNum, int pageSize, String categoryId) {
        LambdaQueryWrapper<HaircutProject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(HaircutProject::getCategoryId,categoryId);
        Page<HaircutProject> page = new Page<>(pageNum,pageSize);
        haircutProjectMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }


}
