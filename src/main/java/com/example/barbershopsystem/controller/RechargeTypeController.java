package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.Hairstyle;
import com.example.barbershopsystem.domain.RechargeType;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.RechargeTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (RechargeType)表控制层
 *
 * @author makejava
 * @since 2024-04-17 16:43:44
 */
@RestController
@RequestMapping("rechargeType")
public class RechargeTypeController  {
    /**
     * 服务对象
     */
    @Resource
    private RechargeTypeService rechargeTypeService;

    @GetMapping("getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return rechargeTypeService.getInfoList(pageNum,pageSize);
    }

    @PostMapping("/addInfo")
    public ResponseResult addInfo(@RequestBody RechargeType rechargeType){

        return rechargeTypeService.addInfo(rechargeType);
    }

    @GetMapping("/getInfoById/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return rechargeTypeService.getInfoById(id);
    }
    @PostMapping("/update")
    public ResponseResult update(@RequestBody RechargeType rechargeType){

        return rechargeTypeService.update(rechargeType);
    }
    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return rechargeTypeService.deleteById(id);
    }

    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String name ){
        return rechargeTypeService.fuzzySearch(pageNum,pageSize,name);
    }
    @GetMapping("/getNames")
    public ResponseResult getNames(){
        return rechargeTypeService.getNames();
    }

}

