package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.RechargeRecord;
import com.example.barbershopsystem.domain.RechargeType;
import com.example.barbershopsystem.domain.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 充值记录表(RechargeRecord)表服务接口
 *
 * @author makejava
 *
 */
public interface RechargeRecordService extends IService<RechargeRecord> {

    ResponseResult add(RechargeRecord rechargeRecord);

    ResponseResult getInfoList(int pageNum, int pageSize,String userId);

    ResponseResult getInfoAllList(int pageNum, int pageSize);

    ResponseResult refund(Long id);

    ResponseResult deleteById(Long id);

    ResponseResult likeSelectById(int pageNum, int pageSize, String id);

    ResponseResult getInfoAllListByOut(int pageNum, int pageSize);

    ResponseResult getIncomeAndOutcome(int pageNum, int pageSize);

    ResponseResult getInfoById(Long id);


    ResponseResult dailyIncomeAndExpenditure();

    ResponseResult MonthIncomeAndExpenditure();

    ResponseResult YearIncomeAndExpenditure();

    ResponseResult likeSelectByIdanddNoRefund(int pageNum, int pageSize, String id);
}
