package com.example.barbershopsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.barbershopsystem.domain.HaircutProject;
import org.apache.ibatis.annotations.Mapper;


/**
 * 理发项目管理表(HaircutProject)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-17 20:22:13
 */
@Mapper
public interface HaircutProjectMapper extends BaseMapper<HaircutProject> {

}
