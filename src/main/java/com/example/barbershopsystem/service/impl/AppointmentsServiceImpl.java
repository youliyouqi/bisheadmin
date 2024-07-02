package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.*;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.*;
import com.example.barbershopsystem.service.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * (Appointments)表服务实现类
 *
 * @author makejava
 *
 */
@Service("appointmentsService")
public class AppointmentsServiceImpl extends ServiceImpl<AppointmentsMapper, Appointments> implements AppointmentsService {
    @Autowired
    private AppointmentsMapper appointmentsMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HaircutProjectMapper haircutProjectMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserBalanceMapper userBalanceMapper;
    @Override
    @Transactional
    public ResponseResult  addAppointmentInfo(Appointments appointments) {
        System.out.println("传回来的数据"+appointments);
        Date estimatedTime = appointments.getEstimatedTime();
        // 创建一个 Calendar 实例，并将预计时间设置为当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(estimatedTime);
        // 将预计时间减去30分钟
        calendar.add(Calendar.MINUTE, -30);
        // 获取减去30分钟后的时间
        Date reminderTime = calendar.getTime();
        // 将减去30分钟后的时间设置为提醒时间
        appointments.setReminderTime(reminderTime);
        //0 未发送 1发送
        appointments.setReminderStatus(0);
        //预约状态  0 未审核 1已通过
        appointments.setStatus(0);
        //是否已读 0 未读 1 已读
        appointments.setIsRead(0);
        User user = userMapper.selectById(appointments.getBarberId());
        appointments.setBarberName(user.getUserName());
        int insert = appointmentsMapper.insert(appointments);
        if (insert<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}
        //更新页面人数
        HaircutProject haircutProject = haircutProjectMapper.selectById(appointments.getProjectId());
        haircutProject.setMaxCapacity(haircutProject.getMaxCapacity()-1);
        int i = haircutProjectMapper.updateById(haircutProject);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }





    @Override
    public ResponseResult getInfoListByuserId(int pageNum, int pageSize, String userId) {
        LambdaQueryWrapper<Appointments> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Appointments::getUserId,userId);
        Page<Appointments> page = new Page<>(pageNum,pageSize);
        appointmentsMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);

    }


    @Override
    public ResponseResult sendReminderNotification(Long id) {
        System.out.println("被调用了");
        System.out.println("传回的id"+id);
        LambdaQueryWrapper<Appointments> lambdaQueryWrapper  = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Appointments::getReminderStatus,0)
                .eq(Appointments::getUserId,id)
                .notIn(Appointments::getStatus,2)
                .lt(Appointments::getReminderTime, LocalDateTime.now());
        System.out.println("LocalDateTime"+LocalDateTime.now());
        Appointments appointments = appointmentsMapper.selectOne(lambdaQueryWrapper);
        System.out.println("查出来的记录"+appointments);
        if (Objects.isNull(appointments)){return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);}

        // 创建更新条件
        LambdaUpdateWrapper<Appointments> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Appointments::getId, appointments.getId()); // 根据预约的id更新
        // 更新提醒状态为1（已提醒）
        appointments.setReminderStatus(1);
        appointmentsMapper.update(appointments,updateWrapper);
        // 返回成功结果
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    @Override
    @Transactional
    public ResponseResult updateCancle(Long id) {
        Appointments appointments = appointmentsMapper.selectById(id);
        appointments.setStatus(2);
        int i = appointmentsMapper.updateById(appointments);

        // 判断是否更新成功
        if (i > 0) {

            HaircutProject haircutProject = haircutProjectMapper.selectById(appointments.getProjectId());
            haircutProject.setMaxCapacity(haircutProject.getMaxCapacity()+1);
            int i2 = haircutProjectMapper.updateById(haircutProject);
            if (i2<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public ResponseResult secondConfirmation(Long id) {
        Appointments appointments = appointmentsMapper.selectById(id);
        appointments.setStatus(1);
        int i = appointmentsMapper.updateById(appointments);

        // 判断是否更新成功
        if (i > 0) {
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication+"logout");
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        String userType = loginUser.getUser().getUserType();
        if (userType.equals("2")){
            LambdaQueryWrapper<Appointments> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Appointments::getBarberId,loginUser.getUser().getId());
            Page<Appointments> page = new Page<>(pageNum,pageSize);
            appointmentsMapper.selectPage(page,lambdaQueryWrapper);
            return ResponseResult.okResult(page);
        }
        Page<Appointments> page = new Page<>(pageNum,pageSize);
        appointmentsMapper.selectPage(page,null);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String name) {
        LambdaQueryWrapper<Appointments> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Appointments::getId,name);
        Page<Appointments> page = new Page<>(pageNum,pageSize);
        appointmentsMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = appointmentsMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getAnnounInfoById(Long id) {

        Appointments appointments = appointmentsMapper.selectById(id);
        if (Objects.isNull(appointments)){return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);}
        return ResponseResult.okResult(appointments);
    }

    @Override
    public ResponseResult approval(Appointments appointments) {
        System.out.println(appointments+"审核传来的订单");
        Appointments appointments1 = appointmentsMapper.selectById(appointments.getId());
        System.out.println("状态"+appointments.getStatus());
        if (appointments.getStatus()==3){
              //通过审核 ，添加到订单
            Orders orders = new Orders();
            // 0 未完成服务  1 完成服务
            orders.setStatus(0);
            orders.setBarberid(appointments1.getBarberId());
            orders.setBarbername(appointments1.getBarberName());
            orders.setProjectid(appointments1.getProjectId());
            orders.setProjectname(appointments1.getProjectName());
            orders.setUserid(appointments1.getUserId());
            HaircutProject haircutProject = haircutProjectMapper.selectById(appointments1.getProjectId());
            orders.setProjectprice(haircutProject.getPrice());
            orders.setPicture(haircutProject.getImageUrl());
            User user = userMapper.selectById(appointments1.getUserId());
            orders.setUsername(user.getUserName());
            ordersMapper.insert(orders);

        }else {
            //订单数回复

            HaircutProject haircutProject = haircutProjectMapper.selectById(appointments1.getProjectId());
            haircutProject.setMaxCapacity(haircutProject.getMaxCapacity()+1);
            int i2 = haircutProjectMapper.updateById(haircutProject);
            if (i2<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        }

        appointments1.setStatus(appointments.getStatus());
        int i = appointmentsMapper.updateById(appointments1);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult checkCurrentAppointmentRecord(Long id) {
        //查看当前用户的预约记录
        LambdaQueryWrapper<Appointments> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Appointments::getUserId,id).in(Appointments::getStatus,0,1);
        Long count = appointmentsMapper.selectCount(lambdaQueryWrapper);
        if (count>=1){
            return ResponseResult.errorResult(AppHttpCodeEnum.EXIST_APPOINTMENT);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getInfoListByBarberId(int pageNum, int pageSize, Long userId) {
        LambdaQueryWrapper<Appointments> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Appointments::getBarberId,userId);
        Page page = new Page<>(pageNum,pageSize);
        appointmentsMapper.selectPage(page,lambdaQueryWrapper);

        return ResponseResult.okResult(page);
    }
}
