package com.example.barbershopsystem.domain;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 发型表(Hairstyle)表实体类
 *
 * @author makejava
 * @since 2024-04-17 13:04:21
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("hairstyle")
public class Hairstyle  {
    //发型ID@TableId
    private Integer id;

    //发型名称
    private String name;
    //发型描述
    private String description;
    //发型价格
    private Double price;
    //发型时长（分钟）
    private Integer duration;
    //发型图片链接
    private String imageUrl;
    //创建时间

    private Date createTime;
    //更新时间

    private Date updateTime;



}
