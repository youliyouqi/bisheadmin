package com.example.barbershopsystem.domain;

import java.util.Date;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 理发师工资表(BarberSalary)表实体类
 *
 * @author makejava
 * @since 2024-04-30 02:31:31
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("barber_salary")
public class BarberSalary  {
    //主键@TableId
    private Long id;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //理发师账号
    private Long barberId;
    //理发师姓名
    private String barberName;
    //工资发放月份
    private String salaryMonth;
    //奖金提成
    private Double bonusCommission;
    //基本工资
    private Double baseSalary;
    //考勤扣款
    private Double attendanceDeduction;
    //实发工资
    private Double actualSalary;
    //备注
    private String remarks;

    /**
     * 用户类型（0管理员，1普通用户）
     */
    private String userType;

}
