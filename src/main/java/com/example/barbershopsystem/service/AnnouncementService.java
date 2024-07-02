package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.Announcement;
import com.example.barbershopsystem.domain.ResponseResult;
import org.springframework.http.ResponseEntity;


/**
 * 公告信息(Announcement)表服务接口
 *
 * @author makejava
 * @since 2024-04-12 14:10:06
 */
public interface AnnouncementService extends IService<Announcement> {

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult addInfo(Announcement announcement);

    ResponseResult getAnnounInfoById(Long id);

    ResponseResult updateInfo(Announcement announcement);

    ResponseResult deleteById(Long id);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String title);

    ResponseResult getInfoListByTypeName(int pageNum, int pageSize, String typeName);

    ResponseResult addClick(Long id);

    ResponseResult getAnnounInfoByClickNum();

    ResponseResult getAnnounInfoBycreateTime();

    ResponseResult getInfoListByUserId(int pageNum, int pageSize, String userId);
}
