package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.UserAnnouncementInteraction;
import com.example.barbershopsystem.service.UserAnnouncementInteractionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 用户-公告信息互动表(UserAnnouncementInteraction)表控制层
 *
 * @author makejava
 * @since 2024-04-15 21:04:29
 */
@RestController
@RequestMapping("userAnnouncementInteraction")
public class UserAnnouncementInteractionController  {
    /**
     * 服务对象
     */
    @Resource
    private UserAnnouncementInteractionService userAnnouncementInteractionService;

    @PostMapping("addZan")
    public ResponseResult addZan(@RequestBody UserAnnouncementInteraction announcementInteraction){
        return userAnnouncementInteractionService.addZan(announcementInteraction);
    }

    @PostMapping("cancelZan")
    public ResponseResult cancelZan(@RequestBody UserAnnouncementInteraction announcementInteraction){
        return userAnnouncementInteractionService.cancelZan(announcementInteraction);
    }

    @PostMapping("selectNum")
    public ResponseResult selectNmu(@RequestBody UserAnnouncementInteraction announcementInteraction){
        return userAnnouncementInteractionService.selectNmu(announcementInteraction);
    }
    @PostMapping("addCollect")
    public ResponseResult addCollect(@RequestBody UserAnnouncementInteraction announcementInteraction){
        return userAnnouncementInteractionService.addCollect(announcementInteraction);
    }
    @PostMapping("cancelCollect")
    public ResponseResult cancelCollect(@RequestBody UserAnnouncementInteraction announcementInteraction){
        return userAnnouncementInteractionService.cancelCollect(announcementInteraction);
    }

}

