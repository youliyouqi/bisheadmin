package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.Aboutus;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.Systemintro;
import com.example.barbershopsystem.service.AboutusService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 关于我们(Aboutus)表控制层
 *
 * @author makejava
 * @since 2024-04-16 17:20:33
 */
@RestController
@RequestMapping("aboutus")
public class AboutusController  {
    /**
     * 服务对象
     */
    @Resource
    private AboutusService aboutusService;
    @GetMapping("getInfo")
    public ResponseResult getInfo()  {
        return aboutusService.getInfo();
    }

    @GetMapping("/getInfo/{id}")
    public ResponseResult getInfo(@PathVariable Long id) {
        return aboutusService.getInfoById(id);
    }
    @PostMapping("/updateInfo")
    public ResponseResult updateInfo(@RequestBody Aboutus aboutus){return aboutusService.updateInfo(aboutus);}
}

