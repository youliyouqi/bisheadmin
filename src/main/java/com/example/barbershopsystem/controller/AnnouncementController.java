package com.example.barbershopsystem.controller;


import  com.example.barbershopsystem.domain.Announcement;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.AnnouncementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 公告信息(Announcement)表控制层
 *
 * @author makejava
 *
 */
@RestController
@RequestMapping("announcement")
public class AnnouncementController  {
    /**
     * 服务对象
     */
    @Resource
    private AnnouncementService announcementService;
   @GetMapping("/getInfoList")
   public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
       return announcementService.getInfoList(pageNum, pageSize);
   }
    @GetMapping("/getInfoListByTypeName")
    public ResponseResult getInfoListByTypeName(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String typeName){
        return announcementService.getInfoListByTypeName(pageNum, pageSize,typeName);
    }


    @PostMapping("/addInfo")
    public ResponseResult addInfo(@RequestBody Announcement announcement){
       return announcementService.addInfo(announcement);
   }


   @GetMapping("/getAnnounInfoById/{id}")
    public ResponseResult getAnnounInfoById(@PathVariable Long id){
       return announcementService.getAnnounInfoById(id);
   }

   @PostMapping("/updateInfo")
    public ResponseResult updateInfo(@RequestBody Announcement announcement){
       System.out.println("修改分类信息传入的实体"+announcement);
       return announcementService.updateInfo(announcement);
   }

   @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
       return announcementService.deleteById(id);
    }

    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String title ){
       return announcementService.fuzzySearch(pageNum,pageSize,title);
    }

    @PostMapping("/addClick")
    public ResponseResult addClick(@RequestBody Long id) {
       return  announcementService.addClick(id);
   }
    @GetMapping("/getAnnounInfoByClickNum")
    public ResponseResult getAnnounInfoByClickNum(){
        return announcementService.getAnnounInfoByClickNum();
    }
    @GetMapping("/getAnnounInfoBycreateTime")
    public ResponseResult getAnnounInfoBycreateTime( ){
        return announcementService.getAnnounInfoBycreateTime();
    }



    @GetMapping("/getInfoListByUserId")
    public ResponseResult getInfoListByUserId(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String userId ){
        return announcementService.getInfoListByUserId(pageNum,pageSize,userId);
    }
}

