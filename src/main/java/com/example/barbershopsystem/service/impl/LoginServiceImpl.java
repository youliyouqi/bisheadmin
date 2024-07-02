package com.example.barbershopsystem.service.impl;

import com.example.barbershopsystem.domain.LoginUser;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.User;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.handle.SystemException;
import com.example.barbershopsystem.service.LoginService;
import com.example.barbershopsystem.utils.JwtUtil;
import com.example.barbershopsystem.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 尤里尤气
 * Created on 2024/4/8-1:00
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {

        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //如果认证没通过，给出相应提示
       if (Objects.isNull(authenticate)){
           return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
       }
        //如果认证通过，使用userId生成一个jwt,jwt存入ResponseResult 返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        map.put("userType", loginUser.getUser().getUserType());
        map.put("userName", loginUser.getUser().getUserName());
        map.put("userNickName", loginUser.getUser().getNickName());
        map.put("userId", loginUser.getUser().getId().toString());
        map.put("status", loginUser.getUser().getStatus());

        //把完整用户信息存入redis，userid作为key
        redisCache.setCacheObject("login:"+userid,loginUser);
        return new ResponseResult(200,"登录成功",map);
    }


    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication+"logout");
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        Long userid = loginUser.getUser().getId();
        System.out.println("退出id"+userid);
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"退出成功");
    }
}
