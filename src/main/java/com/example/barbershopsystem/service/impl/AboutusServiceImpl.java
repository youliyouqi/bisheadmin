package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.Aboutus;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.Systemintro;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.AboutusMapper;
import com.example.barbershopsystem.service.AboutusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 关于我们(Aboutus)表服务实现类
 *
 * @author makejava
 * @since 2024-04-16 17:20:34
 */
@Service("aboutusService")
public class AboutusServiceImpl extends ServiceImpl<AboutusMapper, Aboutus> implements AboutusService {
    @Autowired
    private AboutusMapper aboutusMapper;

    @Override
    public ResponseResult getInfo() {
        List<Aboutus> aboutuses = aboutusMapper.selectList(null);
        return ResponseResult.okResult(aboutuses);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        Aboutus aboutus = aboutusMapper.selectById(id);
        return ResponseResult.okResult(aboutus);
    }

    @Override
    public ResponseResult updateInfo(Aboutus aboutus) {
        int i = aboutusMapper.updateById(aboutus);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
