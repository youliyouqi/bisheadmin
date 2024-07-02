package com.example.barbershopsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.barbershopsystem.domain.Announcement;
import org.apache.ibatis.annotations.Mapper;


/**
 * 公告信息(Announcement)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-12 14:10:06
 */
@Mapper
public interface AnnouncementMapper extends BaseMapper<Announcement> {

}
