package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.Carousel;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.Systemintro;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.SystemintroMapper;
import com.example.barbershopsystem.service.SystemintroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 系统简介(Systemintro)表服务实现类
 *
 * @author makejava
 * @since 2024-04-16 14:23:39
 */
@Service("systemintroService")
public class SystemintroServiceImpl extends ServiceImpl<SystemintroMapper, Systemintro> implements SystemintroService {
    @Autowired
    private SystemintroMapper systemintroMapper;

    @Override
    public ResponseResult getInfo()  {
        List<Systemintro> systemintros = systemintroMapper.selectList(null);
        return ResponseResult.okResult(systemintros);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        Systemintro systemintro = systemintroMapper.selectById(id);
        return ResponseResult.okResult(systemintro);
    }

    @Override
    public ResponseResult updateInfo(Systemintro systemintro) {
        int count = systemintroMapper.updateById(systemintro);
        if (count<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
