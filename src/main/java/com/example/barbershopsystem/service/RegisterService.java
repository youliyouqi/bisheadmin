package com.example.barbershopsystem.service;

import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.User;

/**
 * @author 尤里尤气
 * Created on 2024/4/8-18:52
 */
public interface RegisterService {
    ResponseResult register(User user);
}
