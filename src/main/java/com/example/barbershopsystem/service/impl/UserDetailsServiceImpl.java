package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.barbershopsystem.domain.LoginUser;
import com.example.barbershopsystem.domain.User;
import com.example.barbershopsystem.mapper.MenuMapper;
import com.example.barbershopsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author 尤里尤气
 * Created on 2024/4/7-23:38
 * 重写UserDetailsService  查询用户信息进行认证
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //1 管理员 2 用户 3理发师
        queryWrapper.eq(User::getUserName,username);

        User user = userMapper.selectOne(queryWrapper);
        System.out.println("loadUserByUsername查出来的用户"+user);
        if (Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误!");
        }
        //todo 查询权限信息
        List<String> perms = menuMapper.selectPermsByUserId(user.getId());

        return new LoginUser(user,perms);

    }
}
