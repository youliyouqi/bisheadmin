package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.LeaveApplication;
import com.example.barbershopsystem.domain.Orders;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.LeaveApplicationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 请假申请表(LeaveApplication)表控制层
 *
 * @author makejava
 *
 */
@RestController
@RequestMapping("leaveApplication")
public class LeaveApplicationController {
    /**
     * 服务对象
     */
    @Resource
    private LeaveApplicationService leaveApplicationService;
    @GetMapping("/getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return leaveApplicationService.getInfoList(pageNum, pageSize);

    }
    @GetMapping("/getInfoById/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return leaveApplicationService.getInfoById(id);
    }
    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String name ){
        return leaveApplicationService.fuzzySearch(pageNum,pageSize,name);
    }

    @PostMapping("/approval")
    public ResponseResult approval(@RequestBody LeaveApplication leaveApplication){
        return leaveApplicationService.approval(leaveApplication);
    }

    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return leaveApplicationService.deleteById(id);
    }
    @GetMapping("/cancleApplication/{id}")
    public ResponseResult cancleApplication(@PathVariable Long id){
        return leaveApplicationService.cancleApplication(id);
    }

    @PostMapping("/add")
    public ResponseResult add(@RequestBody LeaveApplication leaveApplication){
        return leaveApplicationService.add(leaveApplication);
    }
}

