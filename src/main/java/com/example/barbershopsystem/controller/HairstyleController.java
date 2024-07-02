package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.Announcement;
import com.example.barbershopsystem.domain.Hairstyle;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.HairstyleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * 发型表(Hairstyle)表控制层
 *
 * @author makejava
 * @since 2024-04-17 13:04:21
 */
@RestController
@RequestMapping("hairstyle")
public class HairstyleController  {
    /**
     * 服务对象
     */
    @Resource
    private HairstyleService hairstyleService;

    @GetMapping("getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
         return hairstyleService.getInfoList(pageNum,pageSize);
    }

    @GetMapping("/getInfoById/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return hairstyleService.getInfoById(id);
    }

    @PostMapping("/addHairstyle")
    public ResponseResult addHairstyle(@RequestBody Hairstyle hairstyle){

        return hairstyleService.addHairstyle(hairstyle);
    }


    @PostMapping("/update")
    public ResponseResult update(@RequestBody Hairstyle hairstyle){

        return hairstyleService.update(hairstyle);
    }
    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return hairstyleService.deleteById(id);
    }

    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String name ){
        return hairstyleService.fuzzySearch(pageNum,pageSize,name);
    }

    @GetMapping("/getNames")
    public ResponseResult getNames( ){
        return hairstyleService.getNames();
    }


}

