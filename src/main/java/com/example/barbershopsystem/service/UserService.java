package com.example.barbershopsystem.service;

import com.example.barbershopsystem.domain.PasswordRequest;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.User;

/**
 * @author 尤里尤气
 * Created on 2024/4/10-14:23
 */
public interface UserService {
    ResponseResult getUserInfo(String userName);

    ResponseResult updateUserInfo(User user);

    ResponseResult restPassword(User user);

    ResponseResult getInfoList(int pageNum, int pageSize);

    ResponseResult getInfoById(Long id);

    ResponseResult updateStatus(User user);

    ResponseResult deleteById(Long id);

    ResponseResult fuzzySearch(int pageNum, int pageSize, String userName);

    ResponseResult getInfoListByAdmin(int pageNum, int pageSize);

    ResponseResult adminCreate(User user);

    ResponseResult likeSelectByAdmin(int pageNum, int pageSize, String userName);

    ResponseResult getInfoListByBarber(int pageNum, int pageSize);

    ResponseResult barberCreate(User user);

    ResponseResult likeSelectByBarber(int pageNum, int pageSize, String userName);

    ResponseResult validatePassword(Long userId, PasswordRequest request);

    ResponseResult getBaber();


    ResponseResult getBaberBysuperadmin();

    ResponseResult getBaberByadmin();
}

