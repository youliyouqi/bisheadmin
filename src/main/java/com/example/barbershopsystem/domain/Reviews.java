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
 * (Reviews)表实体类
 *
 * @author makejava
 * @since 2024-04-24 21:01:23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("reviews")
public class Reviews  {
    @TableId
    private Integer id;

    
    private Integer projectid;
    
    private String projectname;
    
    private Double projectprice;
    
    private Long userid;
    
    private String username;
    
    private Long barberid;
    
    private String barbername;
    
    private Integer orderid;
    
    private Integer rating;
    
    private String comment;
    
    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    //图片
    private String picture;

}
