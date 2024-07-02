package com.example.barbershopsystem.domain;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户-公告信息互动表(UserAnnouncementInteraction)表实体类
 *
 * @author makejava
 * @since 2024-04-15 21:04:30
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_announcement_interaction")
public class UserAnnouncementInteraction  {
    @TableId
    private Long id;

    
    private Long userId;
    
    private Long announcementId;
    //是否点赞（0：未点赞，1：已点赞）
    private Integer liked;
    //是否收藏（0：未收藏，1：已收藏）
    private Integer collected;



}
