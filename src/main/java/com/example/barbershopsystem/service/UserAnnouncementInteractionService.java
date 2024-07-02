package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.UserAnnouncementInteraction;


/**
 * 用户-公告信息互动表(UserAnnouncementInteraction)表服务接口
 *
 * @author makejava
 * @since 2024-04-15 21:04:30
 */
public interface UserAnnouncementInteractionService  {

    ResponseResult addZan(UserAnnouncementInteraction announcementInteraction);

    ResponseResult cancelZan(UserAnnouncementInteraction announcementInteraction);

    ResponseResult selectNmu(UserAnnouncementInteraction announcementInteraction);

    ResponseResult addCollect(UserAnnouncementInteraction announcementInteraction);

    ResponseResult cancelCollect(UserAnnouncementInteraction announcementInteraction);
}
