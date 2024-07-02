package com.example.barbershopsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.barbershopsystem.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 尤里尤气
 * Created on 2024/4/8-20:13
 */
@Mapper
public interface RegisterMapper extends BaseMapper<User> {

}
