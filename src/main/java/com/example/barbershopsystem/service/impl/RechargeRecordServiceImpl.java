package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.*;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.RechargeRecordMapper;
import com.example.barbershopsystem.mapper.RechargeTypeMapper;
import com.example.barbershopsystem.mapper.UserBalanceMapper;
import com.example.barbershopsystem.service.RechargeRecordService;
import com.example.barbershopsystem.utils.DownExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 充值记录表(RechargeRecord)表服务实现类
 *
 * @author makejava
 *
 */
@Service("rechargeRecordService")
public class RechargeRecordServiceImpl extends ServiceImpl<RechargeRecordMapper, RechargeRecord> implements RechargeRecordService {
   @Autowired
   private RechargeRecordMapper rechargeRecordMapper;
   @Autowired
   private UserBalanceMapper userBalanceMapper;
   @Autowired
   private RechargeTypeMapper rechargeTypeMapper;
 /*   @Override
    @Transactional
    public ResponseResult add(RechargeRecord rechargeRecord) {
        System.out.println("前端传回的支付信息"+rechargeRecord);
        // 0 已支付  1 退款
        rechargeRecord.setPaymentStatus("0");
        // 0 收入 1支出
        rechargeRecord.setTransactionType("0");
        rechargeRecord.setOperatorId(rechargeRecord.getRechargePersonId());

        int insert = rechargeRecordMapper.insert(rechargeRecord);
        if (insert<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}

        UpdateWrapper<UserBalance> updateWrapper = new UpdateWrapper<>();
        UserBalance user = userBalanceMapper.selectById(rechargeRecord.getRechargePersonId());
        updateWrapper.eq("user_id",rechargeRecord.getRechargePersonId() );
        // 设置更新的字段和值，假设你要更新用户的余额字段为新的余额值
        updateWrapper.set("balance", user.getBalance()+rechargeRecord.getPrice());
        int  update= userBalanceMapper.update(null, updateWrapper);
        if (update<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }*/

    @Override
    @Transactional
    public ResponseResult add(RechargeRecord rechargeRecord) {
        try {
            System.out.println("添加金额"+rechargeRecord);
            //生成用户id
            UUID uuid = UUID.randomUUID();
            //前8位
            rechargeRecord.setId((long) uuid.hashCode() & 0xFFFFFFFFL);
            // 插入充值记录
            rechargeRecord.setPaymentStatus("0");
            rechargeRecord.setTransactionType("0");
            rechargeRecord.setOperatorId(rechargeRecord.getRechargePersonId());
            RechargeType rechargeType = rechargeTypeMapper.selectById(rechargeRecord.getRechargeTypeId());
            rechargeRecord.setRechargeName(rechargeType.getName());
            rechargeRecordMapper.insert(rechargeRecord);
            System.out.println("当前用户id"+rechargeRecord.getRechargePersonId());
            // 更新用户余额
            LambdaUpdateWrapper<UserBalance> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(UserBalance::getUserId,rechargeRecord.getRechargePersonId());
            UserBalance userBalance = userBalanceMapper.selectOne(lambdaUpdateWrapper);
            if (userBalance == null) {
                return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FINDUSER_ERROR);
            }
            userBalance.setBalance(userBalance.getBalance() + rechargeRecord.getPrice());
            userBalanceMapper.updateById(userBalance);

            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        } catch (Exception e) {
            // 异常处理


            log.error("Add recharge record failed: {}",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 回滚事务
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize,String userId) {
        LambdaQueryWrapper<RechargeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RechargeRecord::getRechargePersonId,userId);
        lambdaQueryWrapper.eq(RechargeRecord::getTransactionType,0);
        Page<RechargeRecord> page = new Page<>(pageNum,pageSize);
        rechargeRecordMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoAllList(int pageNum, int pageSize) {
        LambdaQueryWrapper<RechargeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RechargeRecord::getTransactionType,0);
        Page<RechargeRecord> page = new Page<>(pageNum,pageSize);
        rechargeRecordMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    @Transactional
    public ResponseResult refund(Long id) {
        RechargeRecord rechargeRecord = rechargeRecordMapper.selectById(id);
        //已退款
        rechargeRecord.setPaymentStatus("1");
        int i = rechargeRecordMapper.updateById(rechargeRecord);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        Long rechargePersonId = rechargeRecord.getRechargePersonId();
        LambdaQueryWrapper<UserBalance> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserBalance::getUserId,rechargePersonId);
        UserBalance userBalance = userBalanceMapper.selectOne(lambdaQueryWrapper);

        System.out.println("用户信息"+userBalance);
        Double balance = userBalance.getBalance();
        userBalance.setBalance(balance-rechargeRecord.getPrice());
        int  i1 =  userBalanceMapper.updateById(userBalance);
        if (i1<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = rechargeRecordMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult likeSelectById(int pageNum, int pageSize, String id) {
        LambdaQueryWrapper<RechargeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(RechargeRecord::getId,  id);
        Page<RechargeRecord> page = new Page<>(pageNum,pageSize);
        rechargeRecordMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);

    }

    @Override
    public ResponseResult getInfoAllListByOut(int pageNum, int pageSize) {
        LambdaQueryWrapper<RechargeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(RechargeRecord::getTransactionType,1,2);
        Page<RechargeRecord> page = new Page<>(pageNum,pageSize);
        rechargeRecordMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getIncomeAndOutcome(int pageNum, int pageSize) {
        LambdaQueryWrapper<RechargeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RechargeRecord::getPaymentStatus,0);
        Page<RechargeRecord> page = new Page<>(pageNum,pageSize);
        rechargeRecordMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        RechargeRecord rechargeRecord = rechargeRecordMapper.selectById(id);
        if (ObjectUtils.isNull(rechargeRecord)){return ResponseResult.okResult(AppHttpCodeEnum.NOT_FIND_ERROR);}
        return ResponseResult.okResult(rechargeRecord);
    }

    @Override
    public ResponseResult dailyIncomeAndExpenditure() {
        //日收支
        // 获取当天日期
        LocalDate today = LocalDate.now();
        // 获取当天的开始时间和结束时间
        LocalDateTime startTime = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(today, LocalTime.MAX);

        // 构建查询条件，查询在当天范围内的记录
        LambdaQueryWrapper<RechargeRecord> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RechargeRecord::getPaymentStatus,0);
        queryWrapper.between(RechargeRecord::getRechargeTime, startTime, endTime);
// 用于保存每种类型的总金额
        Map<String, Double> totalAmountMap = new HashMap<>();
        totalAmountMap.put("支出", 0.0); // 初始化支出为0
        totalAmountMap.put("收入", 0.0); // 初始化收入1为0
        // 执行查询
        List<RechargeRecord> records = rechargeRecordMapper.selectList(queryWrapper);
        for (RechargeRecord record : records) {
            String transactionType = record.getTransactionType();
            if (transactionType.equals("0")){
                totalAmountMap.put("收入",totalAmountMap.get("收入")+record.getPrice());
            }else {
                totalAmountMap.put("支出",totalAmountMap.get("支出")+record.getPrice());
            }
        }
        totalAmountMap.put("利润",totalAmountMap.get("收入")-totalAmountMap.get("支出"));
        return ResponseResult.okResult(totalAmountMap);
    }


    @Override
    public ResponseResult MonthIncomeAndExpenditure() {
            // 月收支
            // 获取当月的第一天和最后一天
            LocalDate firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            LocalDate lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
            LocalDateTime startOfMonth = LocalDateTime.of(firstDayOfMonth, LocalTime.MIN);
            LocalDateTime endOfMonth = LocalDateTime.of(lastDayOfMonth, LocalTime.MAX);

            // 构建查询条件，查询在当月范围内的记录
            LambdaQueryWrapper<RechargeRecord> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(RechargeRecord::getPaymentStatus, 0);
            queryWrapper.between(RechargeRecord::getRechargeTime, startOfMonth, endOfMonth);

            // 用于保存每种类型的总金额
            Map<String, Double> totalAmountMap = new HashMap<>();
            totalAmountMap.put("支出", 0.0); // 初始化支出为0
            totalAmountMap.put("收入", 0.0); // 初始化收入为0
            // 执行查询
            List<RechargeRecord> records = rechargeRecordMapper.selectList(queryWrapper);
            for (RechargeRecord record : records) {
                String transactionType = record.getTransactionType();
                if (transactionType.equals("0")) {
                    totalAmountMap.put("收入", totalAmountMap.get("收入") + record.getPrice());
                } else {
                    totalAmountMap.put("支出", totalAmountMap.get("支出") + record.getPrice());
                }
            }
            totalAmountMap.put("利润", totalAmountMap.get("收入") - totalAmountMap.get("支出"));
            return ResponseResult.okResult(totalAmountMap);
        }



    @Override
    public ResponseResult YearIncomeAndExpenditure() {
        // 年收支
        // 获取今年的第一天和最后一天
        LocalDate firstDayOfYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDate lastDayOfYear = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        LocalDateTime startOfYear = LocalDateTime.of(firstDayOfYear, LocalTime.MIN);
        LocalDateTime endOfYear = LocalDateTime.of(lastDayOfYear, LocalTime.MAX);

        // 构建查询条件，查询在今年范围内的记录
        LambdaQueryWrapper<RechargeRecord> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RechargeRecord::getPaymentStatus, 0);
        queryWrapper.between(RechargeRecord::getRechargeTime, startOfYear, endOfYear);

        // 用于保存每种类型的总金额
        Map<String, Double> totalAmountMap = new HashMap<>();
        totalAmountMap.put("支出", 0.0); // 初始化支出为0
        totalAmountMap.put("收入", 0.0); // 初始化收入为0
        // 执行查询
        List<RechargeRecord> records = rechargeRecordMapper.selectList(queryWrapper);
        for (RechargeRecord record : records) {
            String transactionType = record.getTransactionType();
            if (transactionType.equals("0")) {
                totalAmountMap.put("收入", totalAmountMap.get("收入") + record.getPrice());
            } else {
                totalAmountMap.put("支出", totalAmountMap.get("支出") + record.getPrice());
            }
        }
        totalAmountMap.put("利润", totalAmountMap.get("收入") - totalAmountMap.get("支出"));
        return ResponseResult.okResult(totalAmountMap);
    }

    @Override
    public ResponseResult likeSelectByIdanddNoRefund(int pageNum, int pageSize, String id) {
        LambdaQueryWrapper<RechargeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(RechargeRecord::getId,  id);
        lambdaQueryWrapper.eq(RechargeRecord::getPaymentStatus,0);
        Page<RechargeRecord> page = new Page<>(pageNum,pageSize);
        rechargeRecordMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }


}
