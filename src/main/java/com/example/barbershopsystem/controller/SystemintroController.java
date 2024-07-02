package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.Carousel;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.Systemintro;
import com.example.barbershopsystem.service.SystemintroService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

/**
 * 系统简介(Systemintro)表控制层
 *
 * @author makejava
 * @since 2024-04-16 14:23:37
 */
@RestController
@RequestMapping("systemintro")
public class SystemintroController  {
    /**
     * 服务对象
     */
    @Resource
    private SystemintroService systemintroService;

    @GetMapping("getInfo")
    public ResponseResult getInfo()  {
        return systemintroService.getInfo();
    }
    @GetMapping("/getInfo/{id}")
    public ResponseResult getInfo(@PathVariable Long id) {
        return systemintroService.getInfoById(id);
    }
    @PostMapping("/updateInfo")
    public ResponseResult updateInfo(@RequestBody Systemintro systemintro){return systemintroService.updateInfo(systemintro);}
}

