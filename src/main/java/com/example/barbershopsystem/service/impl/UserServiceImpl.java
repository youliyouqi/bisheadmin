package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.barbershopsystem.domain.LoginUser;
import com.example.barbershopsystem.domain.PasswordRequest;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.User;

import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.UserMapper;
import com.example.barbershopsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author 尤里尤气
 * Created on 2024/4/10-14:23
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //后天个人中心数据回显
    @Override
    public ResponseResult getUserInfo(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName,userName);
        User user = userMapper.selectOne(queryWrapper);
        return ResponseResult.okResult(user);
    }
    //修改用户信息
    @Override
    public ResponseResult updateUserInfo(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName,user.getUserName());
        int updateNumber = userMapper.update(user, queryWrapper);
        System.out.println("修改后的条数"+updateNumber);
        if (updateNumber<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult restPassword(User user) {
        System.out.println("重置密码"+user);
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userUpdateWrapper.set("password",user.getPassword()).eq("id",user.getId());
        int update = userMapper.update(user, userUpdateWrapper);
        if (update<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserType,'3');
        Page<User> page = new Page<>(pageNum,pageSize);
        userMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        User user = userMapper.selectById(id);
        if (user.getSex().equals("0")){
            user.setSex("男");
        }
        user.setSex("女");
        return ResponseResult.okResult(user);
    }

    @Override
    public ResponseResult updateStatus(User user) {
        System.out.println(user+"启用禁用");
        String status = user.getStatus();
        if (status.equals("0")){
            user.setStatus("1");
        }else {
            user.setStatus("0");
        }
        System.out.println(user+"启用禁用2");
        int i = userMapper.updateById(user);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = userMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String userName) {
        System.out.println("userName"+userName);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getUserName,  userName)
                .and(wrapper -> wrapper.eq(User::getUserType, '3'));
        Page<User> page = new Page<>(pageNum,pageSize);
        userMapper.selectPage(page,lambdaQueryWrapper);
        System.out.println(page);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoListByAdmin(int pageNum, int pageSize) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserType,'1');
        Page<User> page = new Page<>(pageNum,pageSize);
        userMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);

    }

    @Override
    public ResponseResult adminCreate(User user) {
        //生成用户id
        UUID uuid = UUID.randomUUID();
        // 将UUID转换为字符串并移除短划线
        String uuidString = uuid.toString().replace("-", "");

        // 截取前5个字符
        String firstFiveChars = uuidString.substring(0, 5);
        //前8位
        user.setId((long) firstFiveChars.hashCode() & 0xFFFFFFFFL);
        //生成用户随机昵称
        user.setNickName("管理员"+uuid.toString().substring(0,4));
        //设置状态 0 正常 1停用
        user.setStatus(String.valueOf(0));
        //设置用户类型 1 管理员 3 用户 2理发师
        user.setUserType(String.valueOf(1));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        //保存
        int insert = userMapper.insert(user);
        if (insert== 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.REGISTER_ERROR);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult likeSelectByAdmin(int pageNum, int pageSize, String userName) {
        System.out.println("userName"+userName);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getUserName,  userName).and(wrapper -> wrapper.eq(User::getUserType, '1'));
        Page<User> page = new Page<>(pageNum,pageSize);
        userMapper.selectPage(page,lambdaQueryWrapper);
        System.out.println(page);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoListByBarber(int pageNum, int pageSize) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserType,'2');
        Page<User> page = new Page<>(pageNum,pageSize);
        userMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult barberCreate(User user) {
        //生成用户id
        UUID uuid = UUID.randomUUID();
        // 将UUID转换为字符串并移除短划线
        String uuidString = uuid.toString().replace("-", "");

        // 截取前5个字符
        String firstFiveChars = uuidString.substring(0, 5);
        //前8位
        user.setId((long) firstFiveChars.hashCode() & 0xFFFFFFFFL);
        //生成用户随机昵称
        user.setNickName("理发师"+uuid.toString().substring(0,4));
        //设置状态 0 正常 1停用
        user.setStatus(String.valueOf(0));
        //设置用户类型 1 管理员 3 用户 2理发师
        user.setUserType(String.valueOf(2));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user);
        //保存
        int insert = userMapper.insert(user);
        if (insert== 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.REGISTER_ERROR);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult likeSelectByBarber(int pageNum, int pageSize, String userName) {
        System.out.println("userName"+userName);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getUserName,  userName)
                .and(wrapper -> wrapper.eq(User::getUserType, '2'));
        Page<User> page = new Page<>(pageNum,pageSize);
        userMapper.selectPage(page,lambdaQueryWrapper);
        System.out.println(page.getRecords());
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult validatePassword(Long userId, PasswordRequest  request) {

        User user = userMapper.selectById(userId);


        boolean matches = passwordEncoder.matches(request.getPassword(),user.getPassword());
        System.out.println("对比结果"+matches);
        if (!matches){
            return  ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getBaber() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userType = loginUser.getUser().getUserType();
        System.out.println("调用的用户类型为"+userType);
        if (userType.equals("0")){
            //当用户是超级管理员
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getUserType,1);
            List<User> users = userMapper.selectList(lambdaQueryWrapper);
            if (users.isEmpty()){
                return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
            }
            return ResponseResult.okResult(users);
        }else {
            //登录用户不是超级管理员
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getUserType,2);
            List<User> users = userMapper.selectList(lambdaQueryWrapper);
            if (users.isEmpty()){
                return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
            }
            return ResponseResult.okResult(users);
        }

    }

    @Override
    public ResponseResult getBaberBysuperadmin() {
        //当用户是超级管理员
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserType,1);
        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        if (users.isEmpty()){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(users);
    }

    @Override
    public ResponseResult getBaberByadmin() {
        //登录用户不是超级管理员
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserType,2);
        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        if (users.isEmpty()){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(users);
    }


}
