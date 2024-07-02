package com.example.barbershopsystem.service;

import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.User;

/**
 * @author 尤里尤气
 * Created on 2024/4/8-1:00
 */
public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
