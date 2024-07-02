package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.Appointments;
import com.example.barbershopsystem.domain.Orders;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Orders)表控制层
 *
 * @author makejava
 *
 */
@RestController
@RequestMapping("orders")
public class OrdersController  {
    /**
     * 服务对象
     */
    @Resource
    private OrdersService ordersService;
    @GetMapping("/getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return ordersService.getInfoList(pageNum, pageSize);

}
    @PostMapping("/approval")
    public ResponseResult approval(@RequestBody Orders orders){
        return ordersService.approval(orders);
    }
    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String name ){
        return ordersService.fuzzySearch(pageNum,pageSize,name);
    }
    @GetMapping("/getInfoById/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return ordersService.getAInfoById(id);
    }

    @GetMapping("/getInfoListByUserId")
    public ResponseResult getInfoListByUserId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String userId ){
        return ordersService.getInfoListByUserId(pageNum,pageSize,userId);
    }
    @GetMapping("/getInfoListByBarberId")
    public ResponseResult getInfoListByBarberId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String barberId ){
        return ordersService.getInfoListByBarberId(pageNum,pageSize,barberId);
    }

    @GetMapping("/getBaberStatus/{id}")
    public ResponseResult getBaberStatus(@PathVariable Long id){
        return ordersService.getBaberStatus(id);
    }

    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return ordersService.deleteById(id);
    }
}

