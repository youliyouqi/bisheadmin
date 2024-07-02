package com.example.barbershopsystem.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 系统简介(Systemintro)表实体类
 *
 * @author makejava
 * @since 2024-04-16 14:23:39
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("systemintro")
public class Systemintro  {
    //主键@TableId
    private Integer id;

    //创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createTime;
    //标题
    private String title;
    //内容
    private String content;
    //图片1
    private String picture1;
    //图片2
    private String picture2;



}
