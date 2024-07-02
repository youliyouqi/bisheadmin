package com.example.barbershopsystem.controller;



import com.example.barbershopsystem.domain.DiscountActivities;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.DiscountActivitiesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (DiscountActivities)表控制层
 *
 * @author makejava
 *
 */
@RestController
@RequestMapping("discountActivities")
public class DiscountActivitiesController  {
    /**
     * 服务对象
     */
    @Resource
    private DiscountActivitiesService discountActivitiesService;

    @GetMapping("getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return discountActivitiesService.getInfoList(pageNum,pageSize);
    }

    @GetMapping("/getInfoById/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return discountActivitiesService.getInfoById(id);
    }

    @PostMapping("/addDiscountActivities")
    public ResponseResult addDiscountActivities(@RequestBody DiscountActivities discountActivities){

        return discountActivitiesService.addDiscountActivities(discountActivities);
    }


    @PostMapping("/update")
    public ResponseResult update(@RequestBody DiscountActivities discountActivities){

        return discountActivitiesService.update(discountActivities);
    }
    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return discountActivitiesService.deleteById(id);
    }

    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String name ){
        return discountActivitiesService.fuzzySearch(pageNum,pageSize,name);
    }


}

