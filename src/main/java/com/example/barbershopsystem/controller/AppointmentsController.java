package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.Appointments;
import com.example.barbershopsystem.domain.Carousel;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.AppointmentsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Appointments)表控制层
 *
 * @author makejava
 *
 */
@RestController
@RequestMapping("appointments")
public class AppointmentsController  {
    /**
     * 服务对象
     */
    @Resource
    private AppointmentsService appointmentsService;

    @PostMapping("/addAppointmentInfo")
    public ResponseResult addAppointmentInfo(@RequestBody Appointments appointments){
        return appointmentsService.addAppointmentInfo(appointments);
    }
    @GetMapping("/getInfoList")
    public ResponseResult getInfoListByuserId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize,String userId){
        return appointmentsService.getInfoListByuserId(pageNum, pageSize,userId);
    }
    @GetMapping("/sendReminderNotification/{id}")
    public ResponseResult sendReminderNotification(@PathVariable Long id){
        return appointmentsService.sendReminderNotification(id);
    }



    @GetMapping("/checkCurrentAppointmentRecord/{id}")
    public ResponseResult checkCurrentAppointmentRecord(@PathVariable Long id){
        return appointmentsService.checkCurrentAppointmentRecord(id);
    }
    @GetMapping("/updateCancle/{id}")
    public ResponseResult updateCancle(@PathVariable Long id){
        return appointmentsService.updateCancle(id);
    }

    @GetMapping("/secondConfirmation/{id}")
    public ResponseResult secondConfirmation(@PathVariable Long id){
        return appointmentsService.secondConfirmation(id);
    }
    @GetMapping("/getInfoList1")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return appointmentsService.getInfoList(pageNum, pageSize);
    }

    @GetMapping("/getInfoListByBarberId")
    public ResponseResult getInfoListByBarberId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize,Long userId){
        return appointmentsService.getInfoListByBarberId(pageNum, pageSize,userId);
    }
    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String name ){
        return appointmentsService.fuzzySearch(pageNum,pageSize,name);
    }
    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return appointmentsService.deleteById(id);
    }
    @GetMapping("/getInfoById/{id}")
    public ResponseResult getAnnounInfoById(@PathVariable Long id){
        return appointmentsService.getAnnounInfoById(id);
    }


    @PostMapping("/approval")
    public ResponseResult approval(@RequestBody Appointments appointments){
        return appointmentsService.approval(appointments);
    }
}

