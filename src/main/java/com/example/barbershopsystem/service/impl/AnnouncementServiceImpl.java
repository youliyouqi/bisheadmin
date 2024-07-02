package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.Announcement;
import com.example.barbershopsystem.domain.Announcementclassification;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.UserAnnouncementInteraction;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.AnnouncementMapper;
import com.example.barbershopsystem.mapper.AnnouncementclassificationMapper;
import com.example.barbershopsystem.mapper.UserAnnouncementInteractionMapper;
import com.example.barbershopsystem.service.AnnouncementService;
import com.example.barbershopsystem.utils.SecurityUtils;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 公告信息(Announcement)表服务实现类
 *
 * @author makejava
 * @since 2024-04-12 14:10:06
 */
@Service("announcementService")
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {
    @Autowired
    private AnnouncementMapper announcementMapper;

    @Autowired
    private AnnouncementclassificationMapper announcementclassificationMapper;

    @Autowired
    private UserAnnouncementInteractionMapper userAnnouncementInteractionMapper;
    @Override
    @Transactional
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        Page<Announcement> page = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Announcement::getDelFlag,0);
        Page<Announcement> announcementPage = announcementMapper.selectPage(page, queryWrapper);
        for (Announcement record : announcementPage.getRecords()) {
            record.setContent(EmojiParser.parseToUnicode(record.getContent()));
            Announcementclassification announcementclassification = announcementclassificationMapper.selectById(record.getTypename());
            if (announcementclassification != null) {
                record.setTypename(announcementclassification.getTypename());
            }

        }

        return ResponseResult.okResult(announcementPage);
    }

    @Override
    public ResponseResult addInfo(Announcement announcement) {
        System.out.println(announcement+"公告分类传回的信息");
        announcement.setClicknum(0);
        announcement.setThumbsupnum(0);
        announcement.setCrazilynum(0);
        announcement.setStoreupnum(0);
        announcement.setDelFlag(0);
        announcement.setContent(EmojiParser.parseToAliases(announcement.getContent()));
        UUID uuid = UUID.randomUUID();
        announcement.setId((long) uuid.hashCode()& 0xFFFFFFFFL);
        int insert = announcementMapper.insert(announcement);
        if (insert<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseResult getAnnounInfoById(Long id) {
        Announcement announcement = announcementMapper.selectById(id);
        announcement.setContent(EmojiParser.parseToUnicode(announcement.getContent()));
        Announcementclassification announcementclassification = announcementclassificationMapper.selectById(announcement.getTypename());
        if (announcementclassification != null) {
            announcement.setTypename(announcementclassification.getTypename());
        }

        return ResponseResult.okResult(announcement);
    }

    @Override
    public ResponseResult updateInfo(Announcement announcement) {

        int update = announcementMapper.updateById(announcement);
        if (update<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int count = announcementMapper.deleteById(id);
        System.out.println("传回的id"+id);
        System.out.println("删除的条数"+count);
        if (count<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String title) {
           LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper<>();
           queryWrapper.like(Announcement::getTitle,title);
           Page<Announcement> page = new Page<>(pageNum,pageSize);
           announcementMapper.selectPage(page,queryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoListByTypeName(int pageNum, int pageSize, String typeName) {
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Announcement::getTypename,typeName);
        Page<Announcement> page = new Page<>(pageNum,pageSize);
        announcementMapper.selectPage(page,queryWrapper);
        for (Announcement record : page.getRecords()) {
            record.setContent(EmojiParser.parseToUnicode(record.getContent()));
            Announcementclassification announcementclassification = announcementclassificationMapper.selectById(record.getTypename());
            if (announcementclassification != null) {
                record.setTypename(announcementclassification.getTypename());
            }

        }
        return ResponseResult.okResult(page);
    }

    @Override
    @Transactional
    public ResponseResult addClick(Long id) {
        Announcement announcement = announcementMapper.selectById(id);
        announcement.setClicknum(announcement.getClicknum()+1);
        int count = announcementMapper.updateById(announcement);
        if (count<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getAnnounInfoByClickNum() {

        // 创建查询条件构造器
        QueryWrapper<Announcement> queryWrapper = Wrappers.query();
        // 按点击数倒序排序
        queryWrapper.orderByDesc("clicknum");
        // 限制结果数量为 4
        queryWrapper.last("LIMIT 4");
        List<Announcement> announcements = announcementMapper.selectList(queryWrapper);
        return ResponseResult.okResult(announcements);
    }

    @Override
    public ResponseResult getAnnounInfoBycreateTime() {
        // 创建查询条件构造器
        QueryWrapper<Announcement> queryWrapper = Wrappers.query();
        // 按创建时间倒序排序
        queryWrapper.orderByDesc("create_time");
        // 限制结果数量为 4
        queryWrapper.last("LIMIT 4");
        // 执行查询
        List<Announcement> announcements = announcementMapper.selectList(queryWrapper);
        return ResponseResult.okResult(announcements);
    }

    @Override
    @Transactional
    public ResponseResult getInfoListByUserId(int pageNum, int pageSize, String userId) {
        System.out.println("传入的用户id"+userId);
        // 构建查询条件，查询与指定用户相关的公告交互记录
        LambdaQueryWrapper<UserAnnouncementInteraction> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAnnouncementInteraction::getUserId, userId).eq(UserAnnouncementInteraction::getCollected,1);
        List<UserAnnouncementInteraction> announcementInteractions = userAnnouncementInteractionMapper.selectList(queryWrapper);

        // 如果没有查询到相关的公告交互记录，直接返回空结果
        if (announcementInteractions.isEmpty()) {
            return ResponseResult.okResult(Collections.emptyList());
        }
        List<Long> announcementInteractionIds = new ArrayList<>();
        for (UserAnnouncementInteraction announcementInteraction : announcementInteractions) {
            announcementInteractionIds.add(announcementInteraction.getAnnouncementId());
        }
        System.out.println("查出的公告id"+announcementInteractionIds);

        // 构建查询条件，查询与指定用户相关的公告信息列表
        Page<Announcement> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Announcement> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Announcement::getId, announcementInteractionIds);

        // 执行查询，并将结果存储到 page 中
        announcementMapper.selectPage(page, lambdaQueryWrapper);

        // 返回查询结果
        return ResponseResult.okResult(page);
    }

}
