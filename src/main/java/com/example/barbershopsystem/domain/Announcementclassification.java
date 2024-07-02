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
 * 公告信息分类(Announcementclassification)表实体类
 *
 * @author makejava
 * @since 2024-04-11 20:06:02
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("announcementclassification")
public class Announcementclassification  {
    //主键
    @TableId
    private Long id;

    //创建时间

    private Date createtime;
    //分类名称
    private String typename;



}
