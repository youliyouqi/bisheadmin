package com.example.barbershopsystem.controller;

import com.example.barbershopsystem.domain.LoginUser;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.User;
import com.example.barbershopsystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 尤里尤气
 *
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){

        return loginService.login(user);
    }

    @GetMapping ("/user/logout")
    public ResponseResult logout(){

        return loginService.logout();
    }

}
