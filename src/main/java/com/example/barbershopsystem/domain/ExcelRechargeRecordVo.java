package com.example.barbershopsystem.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.barbershopsystem.utils.TransactionTypeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 尤里尤气
 * Created on 2024/4/29-17:37
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelRechargeRecordVo {
    @ExcelProperty("订单编号")
    private Long id;
    @ExcelProperty("订单金额")
    private Double price;


    //充值时间
    @ExcelProperty("交易时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date rechargeTime;

    //交易类型
    @ExcelProperty(value = "交易类型" ,converter = TransactionTypeConverter.class)
    private String transactionType;
    @ExcelProperty("备注")
    private String rechargeName;
}
