package com.example.barbershopsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.barbershopsystem.domain.Aboutus;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.Systemintro;


/**
 * 关于我们(Aboutus)表服务接口
 *
 * @author makejava
 * @since 2024-04-16 17:20:34
 */
public interface AboutusService extends IService<Aboutus> {

    ResponseResult getInfo();

    ResponseResult getInfoById(Long id);

    ResponseResult updateInfo(Aboutus aboutus);
}
