package com.example.barbershopsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.barbershopsystem.domain.Menu;

import java.util.List;

/**
 * @author 尤里尤气
 * Created on 2024/4/8-11:22
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long id);
}
