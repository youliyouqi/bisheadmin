package com.example.barbershopsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.barbershopsystem.domain.Hairstyle;
import org.apache.ibatis.annotations.Mapper;


/**
 * 发型表(Hairstyle)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-17 13:04:21
 */
@Mapper
public interface HairstyleMapper extends BaseMapper<Hairstyle> {

}
