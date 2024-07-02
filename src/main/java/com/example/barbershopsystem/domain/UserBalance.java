package com.example.barbershopsystem.domain;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户金额表(UserBalance)表实体类
 *
 * @author makejava
 * @since 2024-04-20 17:56:18
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_balance")
public class UserBalance  {
    //主键@TableId
    private Long id;

    //用户ID
    private Long userId;
    //账户余额
    private Double balance;
    //最后更新时间
    private Date lastUpdateTime;



}
