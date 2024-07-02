package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.ProjectCategory;
import com.example.barbershopsystem.domain.RechargeType;
import com.example.barbershopsystem.domain.ResponseResult;


/**
 * (ProjectCategory)表服务接口
 *
 * @author makejava
 * @since 2024-04-17 17:55:26
 */
public interface ProjectCategoryService extends IService<ProjectCategory> {

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult addInfo(ProjectCategory projectCategory);

    ResponseResult getInfoById(Long id);

    ResponseResult update(ProjectCategory projectCategory);

    ResponseResult deleteById(Long id);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String name);

    ResponseResult getNames();
}
