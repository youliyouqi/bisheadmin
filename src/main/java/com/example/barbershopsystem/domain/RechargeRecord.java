package com.example.barbershopsystem.domain;

import java.util.Date;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 充值记录表(RechargeRecord)表实体类
 *
 * @author makejava
 * @since 2024-04-22 12:06:53
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("recharge_record")
public class RechargeRecord  {
    //主键@TableId
    @ExcelProperty("订单编号")
    private Long id;

    //充值类型ID
    private Integer rechargeTypeId;
    //充值时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date rechargeTime;
    //充值人ID
    private Long rechargePersonId;
    //支付状态
    private String paymentStatus;
    //操作人ID
    private Long operatorId;
    //交易类型
    private String transactionType;
    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //充值金额

    private Double price;

  private String rechargeName;

}
