package com.example.barbershopsystem.domain;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 理发项目管理表(HaircutProject)表实体类
 *
 * @author makejava
 * @since 2024-04-17 22:46:51
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("haircut_project")
public class HaircutProject  {
    //项目ID@TableId
    private Integer id;

    //项目名称
    private String name;
    //项目描述
    private String description;
    //项目价格
    private Double price;
    //项目时长（分钟）
    private Integer duration;
    //项目图片链接
    private String imageUrl;
    //项目分类ID
    private Integer categoryId;
    //发型ID
    private Integer hairstyleId;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //发型
    private String hairstyle;
    //可预约人数
    private Integer maxCapacity;
    //预约流程
    private String bookingProcess;
    
    private String category;



}
