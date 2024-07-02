package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.Announcementclassification;
import com.example.barbershopsystem.domain.ResponseResult;


/**
 * 公告信息分类(Announcementclassification)表服务接口
 *
 * @author makejava
 * @since 2024-04-11 20:06:03
 */
public interface AnnouncementclassificationService extends IService<Announcementclassification> {

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult addAnnoun(Announcementclassification announcementclassification);

    ResponseResult getAnnounById(Long id);

    ResponseResult updateAnnoun(Announcementclassification announcementclassification);

    ResponseResult deleteAnnounById(Long id);

    ResponseResult likeSelect(int pageNum,int pageSize,String typename);

    ResponseResult getAnnounInfoCLass();
}
