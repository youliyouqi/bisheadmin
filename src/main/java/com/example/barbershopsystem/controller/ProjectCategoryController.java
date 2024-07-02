package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.ProjectCategory;
import com.example.barbershopsystem.domain.RechargeType;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.ProjectCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (ProjectCategory)表控制层
 *
 * @author makejava
 * @since 2024-04-17 17:55:26
 */
@RestController
@RequestMapping("projectCategory")
public class ProjectCategoryController  {
    /**
     * 服务对象
     */
    @Resource
    private ProjectCategoryService projectCategoryService;
    @GetMapping("getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return projectCategoryService.getInfoList(pageNum,pageSize);
    }

    @PostMapping("/addInfo")
    public ResponseResult addInfo(@RequestBody ProjectCategory projectCategory){

        return projectCategoryService.addInfo(projectCategory);
    }

    @GetMapping("/getInfoById/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return projectCategoryService.getInfoById(id);
    }
    @PostMapping("/update")
    public ResponseResult update(@RequestBody ProjectCategory projectCategory){

        return projectCategoryService.update(projectCategory);
    }
    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return projectCategoryService.deleteById(id);
    }

    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String name ){
        return projectCategoryService.fuzzySearch(pageNum,pageSize,name);
    }
    @GetMapping("getNames")
    public ResponseResult getNames(){
        return projectCategoryService.getNames();
    }

}

