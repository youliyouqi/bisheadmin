package com.example.barbershopsystem.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.Announcementclassification;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.AnnouncementclassificationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 公告信息分类(Announcementclassification)表控制层
 *
 * @author makejava
 * @since 2024-04-11 20:05:59
 */
@RestController
@RequestMapping("announcementclassification")
public class AnnouncementclassificationController  {
    /**
     * 服务对象
     */
    @Resource
    private AnnouncementclassificationService announcementclassificationService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param announcementclassification 查询实体
     * @return 所有数据
     */
    @GetMapping("/getAnnounInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        return announcementclassificationService.getInfoList(pageNum,pageSize);
    }

    @PostMapping("/addAnnoun")
    public ResponseResult addAnnoun(@RequestBody Announcementclassification announcementclassification){
        return announcementclassificationService.addAnnoun(announcementclassification);
    }

    @GetMapping("/getAnnounById/{id}")
    public ResponseResult getAnnounById(@PathVariable Long id) {

        return announcementclassificationService.getAnnounById(id);
    }


    @PostMapping("/updateAnnoun")
    public ResponseResult updateAnnoun(@RequestBody Announcementclassification announcementclassification){
        return announcementclassificationService.updateAnnoun(announcementclassification);
    }

    @GetMapping("/deleteAnnounById/{id}")
    public ResponseResult deleteAnnounById(@PathVariable Long id) {
        return announcementclassificationService.deleteAnnounById(id);
    }

    @GetMapping("/likeSelect")
    public ResponseResult likeSelect(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String typename) {

        return announcementclassificationService.likeSelect(pageNum,pageSize,typename);
    }


    @GetMapping("/getAnnounInfoCLass")
    public ResponseResult getAnnounInfoCLass() {
        return announcementclassificationService.getAnnounInfoCLass();
    }



}

