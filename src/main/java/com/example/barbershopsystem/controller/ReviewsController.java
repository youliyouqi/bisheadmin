package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.Orders;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.Reviews;
import com.example.barbershopsystem.service.ReviewsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Reviews)表控制层
 *
 * @author makejava
 *
 */
@RestController
@RequestMapping("reviews")
public class ReviewsController  {
    /**
     * 服务对象
     */
    @Resource
    private ReviewsService reviewsService;

    @GetMapping("/getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return reviewsService.getInfoList(pageNum, pageSize);

    }
    @GetMapping("/getInfoById/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return reviewsService.getInfoById(id);
    }

    @PostMapping("/score")
    public ResponseResult score(@RequestBody Reviews reviews){
        return reviewsService.score(reviews);
    }

    @GetMapping("/getInfoListByUserId")
    public ResponseResult getInfoListByUserId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String userId ){
        return reviewsService.getInfoListByUserId(pageNum,pageSize,userId);
    }
    @GetMapping("/getInfoListByBarberId")
    public ResponseResult getInfoListByBarberId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String barberId ){
        return reviewsService.getInfoListByBarberId(pageNum,pageSize,barberId);
    }

    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String name ){
        return reviewsService.fuzzySearch(pageNum,pageSize,name);
    }
    @GetMapping("/getInfoById1/{id}")
    public ResponseResult getInfoById1(@PathVariable Long id){
        return reviewsService.getInfoById1(id);
    }

    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return reviewsService.deleteById(id);
    }

    @GetMapping("/getBonus/{id}")
    public ResponseResult getBonus(@PathVariable Long id){
        return reviewsService.getBonus(id);
    }

}

