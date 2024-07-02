package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.*;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.BarberSalaryMapper;
import com.example.barbershopsystem.mapper.RechargeRecordMapper;
import com.example.barbershopsystem.mapper.UserMapper;
import com.example.barbershopsystem.service.BarberSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 理发师工资表(BarberSalary)表服务实现类
 *
 * @author makejava
 * @since 2024-04-30 02:31:31
 */
@Service("barberSalaryService")
public class BarberSalaryServiceImpl extends ServiceImpl<BarberSalaryMapper, BarberSalary> implements BarberSalaryService {
    @Autowired
    private BarberSalaryMapper barberSalaryMapper;
    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
            Page<BarberSalary> page = new Page<>(pageNum,pageSize);
            barberSalaryMapper.selectPage(page,null);
            return ResponseResult.okResult(page);

    }

    @Override
    public ResponseResult addInfo(BarberSalary barberSalary) {
        //生成用户id
        UUID uuid = UUID.randomUUID();
        barberSalary.setId((long) uuid.hashCode() & 0xFFFFFFFFL);
        String salaryMonth = barberSalary.getSalaryMonth();
        LocalDateTime dateTime = LocalDateTime.parse(salaryMonth, DateTimeFormatter.ISO_DATE_TIME);
        int month = dateTime.getMonthValue();
        barberSalary.setSalaryMonth(String.valueOf(month+1));
        barberSalary.setActualSalary(barberSalary.getBonusCommission()+barberSalary.getBaseSalary()-barberSalary.getAttendanceDeduction());
        User user = userMapper.selectById(barberSalary.getBarberId());
        barberSalary.setUserType(user.getUserType());
        int insert = barberSalaryMapper.insert(barberSalary);
        if (insert<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}
        RechargeRecord rechargeRecord = new RechargeRecord();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication+"logout");
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        rechargeRecord.setRechargeTypeId(Math.toIntExact(barberSalary.getId()));
        rechargeRecord.setRechargePersonId(barberSalary.getBarberId());
        rechargeRecord.setPaymentStatus("0");
        rechargeRecord.setOperatorId(loginUser.getUser().getId());
        rechargeRecord.setTransactionType("3");//工资支出
        rechargeRecord.setPrice(barberSalary.getActualSalary());
        rechargeRecord.setRechargeName(barberSalary.getRemarks());
        //生成用户id
        UUID uuid2 = UUID.randomUUID();
        rechargeRecord.setId((long) uuid2.hashCode() & 0xFFFFFFFFL);
        rechargeRecordMapper.insert(rechargeRecord);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        BarberSalary barberSalary = barberSalaryMapper.selectById(id);
        if (ObjectUtils.isNull(barberSalary)){return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);}
        return ResponseResult.okResult(barberSalary);
    }

    @Override
    public ResponseResult update(BarberSalary barberSalary) {
        barberSalary.setActualSalary(barberSalary.getBonusCommission()+barberSalary.getBaseSalary()-barberSalary.getAttendanceDeduction());
        System.out.println(barberSalary+"薪水");
        int i = barberSalaryMapper.updateById(barberSalary);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = barberSalaryMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String title) {
        LambdaQueryWrapper<BarberSalary> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(BarberSalary::getBarberName,title);
        Page<BarberSalary> page = new Page<>(pageNum,pageSize);
        barberSalaryMapper.selectPage(page,lambdaQueryWrapper);
        System.out.println("发型展示-搜索出的数据"+page.getRecords());
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoByBaberId(int pageNum, int pageSize,String id) {
        LambdaQueryWrapper<BarberSalary> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BarberSalary::getBarberId,id);
        Page<BarberSalary> page = new Page<>(pageNum,pageSize);
        barberSalaryMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoListBymanager(int pageNum, int pageSize) {
        LambdaQueryWrapper<BarberSalary> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BarberSalary::getUserType,"2");
        Page<BarberSalary> page = new Page<>(pageNum,pageSize);
                barberSalaryMapper.selectPage(page,lambdaQueryWrapper);
                return ResponseResult.okResult(page);

    }
}
