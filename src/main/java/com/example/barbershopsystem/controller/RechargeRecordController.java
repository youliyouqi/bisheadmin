package com.example.barbershopsystem.controller;



import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.barbershopsystem.domain.ExcelRechargeRecordVo;
import com.example.barbershopsystem.domain.RechargeRecord;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.User;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.handle.ExcelExportHandler;
import com.example.barbershopsystem.mapper.RechargeRecordMapper;
import com.example.barbershopsystem.service.RechargeRecordService;
import com.example.barbershopsystem.utils.BeanCopyUtils;
import com.example.barbershopsystem.utils.DownExcel;
import com.example.barbershopsystem.utils.WebUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Book;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 充值记录表(RechargeRecord)表控制层
 *
 * @author makejava
 *
 */
@RestController
@RequestMapping("rechargeRecord")
public class RechargeRecordController{
    /**
     * 服务对象
     */
    @Resource
    private RechargeRecordService rechargeRecordService;

    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;
    @Resource
    private ExcelExportHandler excelExportHandler;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody RechargeRecord rechargeRecord){

        return   rechargeRecordService.add(rechargeRecord);
    }


    @GetMapping("/getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize,String userId){
        return rechargeRecordService.getInfoList(pageNum, pageSize,userId);
    }
    @GetMapping("/getInfoAllList")
    public ResponseResult getInfoAllList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return rechargeRecordService.getInfoAllList(pageNum, pageSize);
    }



    @GetMapping("/refund/{id}")
    public ResponseResult refund(@PathVariable Long id){
        return rechargeRecordService.refund(id);
    }
    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return rechargeRecordService.deleteById(id);
    }


    @GetMapping("/likeSelectById")
    public ResponseResult likeSelectById(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize , String id ){
        return rechargeRecordService.likeSelectById(pageNum,pageSize,id);
    }

    @GetMapping("/likeSelectByIdanddNoRefund")
    public ResponseResult likeSelectByIdanddNoRefund(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize , String id ){
        return rechargeRecordService.likeSelectByIdanddNoRefund(pageNum,pageSize,id);
    }
    @GetMapping("/getInfoAllListByOut")
    public ResponseResult getInfoAllListByOut(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return rechargeRecordService.getInfoAllListByOut(pageNum, pageSize);
    }
    @GetMapping("/getIncomeAndOutcome")
    public ResponseResult getIncomeAndOutcome(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return rechargeRecordService.getIncomeAndOutcome(pageNum, pageSize);
    }
    @GetMapping("/DailyIncomeAndExpenditure")
    public ResponseResult dailyIncomeAndExpenditure(){
        return rechargeRecordService.dailyIncomeAndExpenditure();
    }
    @GetMapping("/MonthIncomeAndExpenditure")
    public ResponseResult MonthIncomeAndExpenditure(){
        return rechargeRecordService.MonthIncomeAndExpenditure();
    }
    @GetMapping("/YearIncomeAndExpenditure")
    public ResponseResult YearIncomeAndExpenditure(){
        return rechargeRecordService.YearIncomeAndExpenditure();
    }

    @GetMapping("/getInfoById/{id}")
    public ResponseResult getInfoById(@PathVariable Long id){
        return rechargeRecordService.getInfoById(id);
    }

  /*  @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            // 设置下载文件的请求头
            DownExcel.setDownLoadHeader("分类.xlsx", response);

            // 获取需要导出的数据
            LambdaQueryWrapper<RechargeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RechargeRecord::getPaymentStatus, 0);
            List<RechargeRecord> rechargeRecords = rechargeRecordMapper.selectList(lambdaQueryWrapper);
            List<ExcelRechargeRecordVo> excelRechargeRecordVos = BeanCopyUtils.copyBeanList(rechargeRecords, ExcelRechargeRecordVo.class);
            // 将数据写入 Excel 中
            EasyExcel.write(response.getOutputStream(), ExcelRechargeRecordVo.class)
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("分类导出")
                    .doWrite(excelRechargeRecordVos);
        } catch (Exception e) {
            // 处理异常
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }*/


    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        //获取需要导出的数据
        LambdaQueryWrapper<RechargeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RechargeRecord::getPaymentStatus, 0);
        List<RechargeRecord> rechargeRecords = rechargeRecordMapper.selectList(lambdaQueryWrapper);
        List<ExcelRechargeRecordVo> excelRechargeRecordVos = BeanCopyUtils.copyBeanList(rechargeRecords, ExcelRechargeRecordVo.class);

        System.out.println(excelRechargeRecordVos+"导出数据");
        excelExportHandler.export(response, "人员表", excelRechargeRecordVos, ExcelRechargeRecordVo.class);
    }


}

