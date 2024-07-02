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
 * (DiscountActivities)表实体类
 *
 * @author makejava
 * @since 2024-04-18 17:30:28
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("discount_activities")
public class DiscountActivities  {
    @TableId
    private Integer id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")

    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")

    private Date endDate;
    
    private String title;
    
    private String discountDetails;
    
    private String imageUrl;
    
    private Date createTime;
    
    private Date updateTime;



}
