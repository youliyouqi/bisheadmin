package com.example.barbershopsystem.controller;

import com.example.barbershopsystem.domain.Carousel;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.service.CarouselImageManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 尤里尤气
 * Created on 2024/4/11-13:26
 */
@RestController
@RequestMapping("/CarouselImageManagement")
public class CarouselImageManagement {
    @Autowired
    private CarouselImageManagementService imageManagementService;
     @GetMapping("/getInfo")
    public ResponseResult getInfo(){
         return imageManagementService.getInfo();
     }


    @GetMapping("/getInfo/{id}")
    public ResponseResult getInfo(@PathVariable Long id) {
         return imageManagementService.getInfoById(id);
    }
    @PostMapping("/updateInfo")
    public ResponseResult updateInfo(@RequestBody Carousel carousel){
        System.out.println(carousel+"111");
         return imageManagementService.updateInfo(carousel);
    }



    @GetMapping("/getImg")
    public ResponseResult getImg(){
        return imageManagementService.getImg();
    }
}
