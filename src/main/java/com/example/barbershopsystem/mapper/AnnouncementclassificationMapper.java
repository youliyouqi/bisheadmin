package com.example.barbershopsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.Announcementclassification;
import org.apache.ibatis.annotations.Mapper;


/**
 * 公告信息分类(Announcementclassification)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-11 20:06:01
 */
@Mapper
public interface AnnouncementclassificationMapper extends BaseMapper<Announcementclassification> {


}
