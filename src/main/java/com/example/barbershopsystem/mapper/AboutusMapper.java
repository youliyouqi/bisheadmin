package com.example.barbershopsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.barbershopsystem.domain.Aboutus;
import org.apache.ibatis.annotations.Mapper;


/**
 * 关于我们(Aboutus)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-16 17:20:34
 *
 */
@Mapper
public interface AboutusMapper extends BaseMapper<Aboutus> {

}
