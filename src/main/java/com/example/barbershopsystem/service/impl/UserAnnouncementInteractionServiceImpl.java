package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.Announcement;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.UserAnnouncementInteraction;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.AnnouncementMapper;
import com.example.barbershopsystem.mapper.UserAnnouncementInteractionMapper;
import com.example.barbershopsystem.service.UserAnnouncementInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户-公告信息互动表(UserAnnouncementInteraction)表服务实现类
 *
 * @author makejava
 * @since 2024-04-15 21:04:30
 */
@Service("userAnnouncementInteractionService")
public class UserAnnouncementInteractionServiceImpl  implements UserAnnouncementInteractionService {
    @Autowired
    private UserAnnouncementInteractionMapper userAnnouncementInteractionMapper;
    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    @Transactional
    public ResponseResult addZan(UserAnnouncementInteraction announcementInteraction) {
        LambdaQueryWrapper<UserAnnouncementInteraction> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAnnouncementInteraction::getAnnouncementId,announcementInteraction.getAnnouncementId());
        lambdaQueryWrapper.eq(UserAnnouncementInteraction::getUserId,announcementInteraction.getUserId());

        Long count = userAnnouncementInteractionMapper.selectCount(lambdaQueryWrapper);
        if (count<=0){
            //从未添加or收藏
            announcementInteraction.setLiked(1);
            announcementInteraction.setCollected(0);
            System.out.println(announcementInteraction+"前端传来的announcementInteraction");
            int insert = userAnnouncementInteractionMapper.insert(announcementInteraction);
            if (insert<=0){
                return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
            }
            Announcement announcement = announcementMapper.selectById(announcementInteraction.getAnnouncementId());
            announcement.setThumbsupnum(announcement.getThumbsupnum()+1);
            announcementMapper.updateById(announcement);
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        //添加点赞
        LambdaUpdateWrapper<UserAnnouncementInteraction> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserAnnouncementInteraction::getLiked,true)
                        .eq(UserAnnouncementInteraction::getAnnouncementId,announcementInteraction.getAnnouncementId())
                .eq(UserAnnouncementInteraction::getUserId,announcementInteraction.getUserId());
        int update = userAnnouncementInteractionMapper.update(null, updateWrapper);
        if (update<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
        Announcement announcement = announcementMapper.selectById(announcementInteraction.getAnnouncementId());
        announcement.setThumbsupnum(announcement.getThumbsupnum()+1);
        announcementMapper.updateById(announcement);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseResult cancelZan(UserAnnouncementInteraction announcementInteraction) {
        LambdaUpdateWrapper<UserAnnouncementInteraction> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserAnnouncementInteraction::getLiked,false)
                .eq(UserAnnouncementInteraction::getAnnouncementId,announcementInteraction.getAnnouncementId())
                .eq(UserAnnouncementInteraction::getUserId,announcementInteraction.getUserId());
        int update = userAnnouncementInteractionMapper.update(null, updateWrapper);
        if (update<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
        Announcement announcement = announcementMapper.selectById(announcementInteraction.getAnnouncementId());

        if ((announcement.getThumbsupnum()-1)<=0){
            announcement.setThumbsupnum(0);
        }else {
            announcement.setThumbsupnum(announcement.getThumbsupnum()-1);
        }
        announcementMapper.updateById(announcement);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult selectNmu(UserAnnouncementInteraction announcementInteraction) {
        if (announcementInteraction.getUserId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        LambdaQueryWrapper<UserAnnouncementInteraction> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAnnouncementInteraction::getAnnouncementId,announcementInteraction.getAnnouncementId());
        lambdaQueryWrapper.eq(UserAnnouncementInteraction::getUserId,announcementInteraction.getUserId());
        Long count = userAnnouncementInteractionMapper.selectCount(lambdaQueryWrapper);
        if (count<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        UserAnnouncementInteraction userAnnouncementInteraction = userAnnouncementInteractionMapper.selectOne(lambdaQueryWrapper);
        return ResponseResult.okResult(userAnnouncementInteraction);
    }

    @Override
    public ResponseResult addCollect(UserAnnouncementInteraction announcementInteraction) {
        LambdaQueryWrapper<UserAnnouncementInteraction> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAnnouncementInteraction::getAnnouncementId,announcementInteraction.getAnnouncementId());
        lambdaQueryWrapper.eq(UserAnnouncementInteraction::getUserId,announcementInteraction.getUserId());

        Long count = userAnnouncementInteractionMapper.selectCount(lambdaQueryWrapper);
        if (count<=0){
            //从未添加or收藏
            announcementInteraction.setLiked(0);
            announcementInteraction.setCollected(1);
            int insert = userAnnouncementInteractionMapper.insert(announcementInteraction);
            if (insert<=0){
                return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);
            }
            Announcement announcement = announcementMapper.selectById(announcementInteraction.getAnnouncementId());
            announcement.setStoreupnum(announcement.getStoreupnum()+1);
            announcementMapper.updateById(announcement);
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }
        //添加点赞
        LambdaUpdateWrapper<UserAnnouncementInteraction> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserAnnouncementInteraction::getCollected,true)
                .eq(UserAnnouncementInteraction::getAnnouncementId,announcementInteraction.getAnnouncementId())
                .eq(UserAnnouncementInteraction::getUserId,announcementInteraction.getUserId());
        int update = userAnnouncementInteractionMapper.update(null, updateWrapper);
        if (update<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
        Announcement announcement = announcementMapper.selectById(announcementInteraction.getAnnouncementId());
        announcement.setStoreupnum(announcement.getStoreupnum()+1);
        announcementMapper.updateById(announcement);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    @Override
    public ResponseResult cancelCollect(UserAnnouncementInteraction announcementInteraction) {
        LambdaUpdateWrapper<UserAnnouncementInteraction> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(UserAnnouncementInteraction::getCollected,0)
                .eq(UserAnnouncementInteraction::getAnnouncementId,announcementInteraction.getAnnouncementId())
                .eq(UserAnnouncementInteraction::getUserId,announcementInteraction.getUserId());
        int update = userAnnouncementInteractionMapper.update(null, updateWrapper);
        if (update<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
        Announcement announcement = announcementMapper.selectById(announcementInteraction.getAnnouncementId());
        if ((announcement.getStoreupnum()-1)<=0){
            announcement.setStoreupnum(0);
        }else {
            announcement.setStoreupnum(announcement.getStoreupnum()-1);
        }

        announcementMapper.updateById(announcement);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
