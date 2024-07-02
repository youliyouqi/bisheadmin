package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.HaircutProject;
import com.example.barbershopsystem.domain.ResponseResult;


/**
 * 理发项目管理表(HaircutProject)表服务接口
 *
 * @author makejava
 * @since 2024-04-17 20:22:13
 */
public interface HaircutProjectService extends IService<HaircutProject> {

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult addInfo(HaircutProject haircutProject);

    ResponseResult getInfoById(Long id);

    ResponseResult update(HaircutProject haircutProject);

    ResponseResult deleteById(Long id);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String name);


    ResponseResult getInfoByCategoryId(int pageNum, int pageSize, String categoryId);
}
