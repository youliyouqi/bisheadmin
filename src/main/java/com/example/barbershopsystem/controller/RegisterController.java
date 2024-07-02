package com.example.barbershopsystem.controller;

import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.User;
import com.example.barbershopsystem.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 尤里尤气
 * Created on 2024/4/8-18:43
 */
@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    @PostMapping("/user/register")
    public ResponseResult register(@RequestBody User user){
       return   registerService.register(user);
    }
}
