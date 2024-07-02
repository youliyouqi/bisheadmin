package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.Systemintro;

import java.text.ParseException;


/**
 * 系统简介(Systemintro)表服务接口
 *
 * @author makejava
 * @since 2024-04-16 14:23:39
 */
public interface SystemintroService extends IService<Systemintro> {

    ResponseResult getInfo();

    ResponseResult getInfoById(Long id);

    ResponseResult updateInfo(Systemintro systemintro);
}
