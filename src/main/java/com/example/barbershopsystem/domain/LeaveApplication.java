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
 * 请假申请表(LeaveApplication)表实体类
 *
 * @author makejava
 * @since 2024-05-01 18:40:55
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("leave_application")
public class LeaveApplication  {
    //主键@TableId
    private Long id;

    //创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    //请假事由
    private String reason;
    //请假开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    //请假结束时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    //理发师账号
    private String barberId;
    //理发师姓名
    private String barberName;
    //审核状态
    private String status;
    //审核回复
    private String reply;
    //审核人ID
    private Long approverId;
    //审核人姓名
    private String approverName;
    //审批时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date approvalTime;



}
