package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.Announcementclassification;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.AnnouncementclassificationMapper;
import com.example.barbershopsystem.service.AnnouncementclassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 公告信息分类(Announcementclassification)表服务实现类
 *
 * @author makejava
 * @since 2024-04-11 20:06:03
 */
@Service("announcementclassificationService")
public class AnnouncementclassificationServiceImpl extends ServiceImpl<AnnouncementclassificationMapper, Announcementclassification> implements AnnouncementclassificationService {

   @Autowired
   private AnnouncementclassificationMapper announcementclassificationMapper;
    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        IPage<Announcementclassification> page = new Page<>(pageNum,pageSize);
        announcementclassificationMapper.selectPage(page,null);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult addAnnoun(Announcementclassification announcementclassification) {
        UUID uuid = UUID.randomUUID();
        announcementclassification.setId((long) uuid.hashCode()& 0xFFFFFFFFL);
        int insertCount = announcementclassificationMapper.insert(announcementclassification);
        if (insertCount<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getAnnounById(Long id) {
        Announcementclassification announcementclassification = announcementclassificationMapper.selectById(id);
        if (Objects.isNull(announcementclassification)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(announcementclassification);
    }

    @Override
    public ResponseResult updateAnnoun(Announcementclassification announcementclassification) {
        int count = announcementclassificationMapper.updateById(announcementclassification);
        if (count<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteAnnounById(Long id) {

        int count = announcementclassificationMapper.deleteById(id);

        if (count<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    @Override
    public ResponseResult likeSelect(int pageNum, int pageSize, String typename) {
        LambdaQueryWrapper<Announcementclassification> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(Announcementclassification::getTypename,typename);
        IPage<Announcementclassification> page = new Page<>(pageNum,pageSize);
        IPage<Announcementclassification> announcementclassificationIPage = announcementclassificationMapper.selectPage(page, queryWrapper);

        return ResponseResult.okResult(announcementclassificationIPage);
    }

    @Override
    public ResponseResult getAnnounInfoCLass() {
        List<Announcementclassification> announcementclassifications = announcementclassificationMapper.selectList(null);
        return ResponseResult.okResult(announcementclassifications);
    }


}
