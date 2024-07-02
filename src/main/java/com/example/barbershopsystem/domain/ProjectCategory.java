package com.example.barbershopsystem.domain;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (ProjectCategory)表实体类
 *
 * @author makejava
 * @since 2024-04-17 17:55:26
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("project_category")
public class ProjectCategory  {
    @TableId
    private Integer id;

    //项目分类名称
    private String name;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;



}
