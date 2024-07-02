package com.example.barbershopsystem.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 尤里尤气
 * Created on 2024/5/1-16:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelBarberSalaryVo {


    //创建时间
    @ExcelProperty("发放时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //理发师姓名
    @ExcelProperty("理发师")
    private String barberName;
    //工资发放月份
    @ExcelProperty("工资发放月份")
    private String salaryMonth;
    //奖金提成
    @ExcelProperty("奖金提成")
    private Double bonusCommission;
    //基本工资
    @ExcelProperty("基本工资")
    private Double baseSalary;
    //考勤扣款
    @ExcelProperty("考勤扣款")
    private Double attendanceDeduction;
    //实发工资
    @ExcelProperty("实发工资")
    private Double actualSalary;
    //备注
    @ExcelProperty("备注")
    private String remarks;

}
