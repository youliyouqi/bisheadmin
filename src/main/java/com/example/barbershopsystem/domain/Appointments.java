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
 * (Appointments)表实体类
 *
 * @author makejava
 * @since 2024-04-22 18:15:45
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("appointments")
public class Appointments  {
    @TableId
    private Integer id;

    
    private Integer projectId;
    
    private Long userId;
    
    private Long barberId;
    private String barberName;
    private String projectName;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")

    private Date estimatedTime;
    
    private Integer status;
    
    private Integer isRead;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date reminderTime;
    
    private Integer reminderStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;



}
