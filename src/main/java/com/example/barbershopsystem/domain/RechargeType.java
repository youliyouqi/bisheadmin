package com.example.barbershopsystem.domain;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (RechargeType)表实体类
 *
 * @author makejava
 * @since 2024-04-17 16:43:44
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("recharge_type")
public class RechargeType  {
    @TableId
    private Integer id;

    //充值类型名称
    private String name;
    //充值金额
    private Double price;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;



}
