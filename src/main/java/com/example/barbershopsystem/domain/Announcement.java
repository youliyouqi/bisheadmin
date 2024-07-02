package com.example.barbershopsystem.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 公告信息(Announcement)表实体类
 *
 * @author makejava
 * @since 2024-04-12 14:10:06
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("announcement")
public class Announcement  {
    //主键@TableId
    private Long id;

    //标题
    private String title;
    //简介
    private String introduction;
    //分类名称
    private String typename;
    //发布人
    private String name;
    //点击次数
    private Integer clicknum;
    //赞
    private Integer thumbsupnum;
    //踩
    private Integer crazilynum;
    //收藏数
    private Integer storeupnum;
    //图片
    private String picture;
    //内容
    private String content;
    

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    /**
     * 更新时间
*/
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}
