package com.example.barbershopsystem.controller;

import com.example.barbershopsystem.domain.PasswordRequest;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.User;

import com.example.barbershopsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 尤里尤气
 * Created on 2024/4/10-13:37
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/CurrentUserInfo/{userName}")
    public ResponseResult getUserInfo(@PathVariable String userName){

        return userService.getUserInfo(userName);
    }
    @PostMapping("/update")
    public ResponseResult updateUserInfo(@RequestBody User user){

        return userService.updateUserInfo(user);
    }
    @PostMapping("/updateStatus")
    public ResponseResult updateStatus(@RequestBody User user){

        return userService.updateStatus(user);
    }

    @PostMapping("/restPass")
    public ResponseResult restPassword(@RequestBody User user){

        return userService.restPassword(user);
    }

    /**
     * 获取用户
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getInfoList")
    public ResponseResult getInfoList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return userService.getInfoList(pageNum, pageSize);
    }


    @GetMapping("/getInfoById/{id}")
    public ResponseResult getAnnounInfoById(@PathVariable Long id){
        return userService.getInfoById(id);
    }
    @GetMapping("/deleteById/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return userService.deleteById(id);
    }
    @GetMapping("/likeSelect")
    public ResponseResult fuzzySearch(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String userName ){
        return userService.fuzzySearch(pageNum,pageSize,userName);
    }

    @GetMapping("/getInfoListByAdmin")
    public ResponseResult getInfoListByAdmin(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return userService.getInfoListByAdmin(pageNum, pageSize);
    }
    @GetMapping("/getBaberBysuperadmin")
    public ResponseResult getBaberBysuperadmin(){
        return userService.getBaberBysuperadmin();
    }
    @GetMapping("/getBaberByadmin")
    public ResponseResult getBaberByadmin(){
        return userService.getBaberByadmin();
    }

    @PostMapping("/adminCreate")
    public ResponseResult adminCreate(@RequestBody User user){
        return   userService.adminCreate(user);
    }

    @GetMapping("/likeSelectByAdmin")
    public ResponseResult likeSelectByAdmin(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String userName ){
        return userService.likeSelectByAdmin(pageNum,pageSize,userName);
    }
    @GetMapping("/getInfoListByBarber")
    public ResponseResult getInfoListByBarber(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize){
        return userService.getInfoListByBarber(pageNum, pageSize);
    }

    @PostMapping("/barberCreate")
    public ResponseResult barberCreate(@RequestBody User user){
        return   userService.barberCreate(user);
    }

    @GetMapping("/likeSelectByBarber")
    public ResponseResult likeSelectByBarber(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize ,String userName ){
        return userService.likeSelectByBarber(pageNum,pageSize,userName);
    }

    @PostMapping("/validatePassword/{userId}")
    public ResponseResult validatePassword(@PathVariable Long userId,@RequestBody PasswordRequest request){
        return userService.validatePassword(userId,request);
    }

}
