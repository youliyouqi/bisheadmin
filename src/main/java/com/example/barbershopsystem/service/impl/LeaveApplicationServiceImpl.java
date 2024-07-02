package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.LeaveApplication;
import com.example.barbershopsystem.domain.LoginUser;
import com.example.barbershopsystem.domain.Orders;
import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.LeaveApplicationMapper;
import com.example.barbershopsystem.service.LeaveApplicationService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 请假申请表(LeaveApplication)表服务实现类
 *
 * @author makejava
 *
 */
@Service("leaveApplicationService")
public class LeaveApplicationServiceImpl extends ServiceImpl<LeaveApplicationMapper, LeaveApplication> implements LeaveApplicationService {

    @Autowired
    private LeaveApplicationMapper leaveApplicationMapper;
    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userType = loginUser.getUser().getUserType();
        if (userType.equals("2")){
            LambdaQueryWrapper<LeaveApplication> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(LeaveApplication::getBarberId,loginUser.getUser().getId());
            Page<LeaveApplication> page = new Page<>(pageNum,pageSize);
            leaveApplicationMapper.selectPage(page,lambdaQueryWrapper);
            return ResponseResult.okResult(page);
        }
        Page<LeaveApplication> page = new Page<>(pageNum,pageSize);
        leaveApplicationMapper.selectPage(page,null);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        LeaveApplication leaveApplication = leaveApplicationMapper.selectById(id);
        if (ObjectUtils.isNull(leaveApplication)){return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);}
        return ResponseResult.okResult(leaveApplication);
    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String name) {
        LambdaQueryWrapper<LeaveApplication> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(LeaveApplication::getBarberName,name);
        Page<LeaveApplication> page = new Page<>(pageNum,pageSize);
        leaveApplicationMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult approval(LeaveApplication leaveApplication) {
        LeaveApplication oldleaveApplication = leaveApplicationMapper.selectById(leaveApplication.getId());
        oldleaveApplication.setStatus(leaveApplication.getStatus());
        int i = leaveApplicationMapper.updateById(oldleaveApplication);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = leaveApplicationMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult add(LeaveApplication leaveApplication) {
        System.out.println("传回来的请假信息"+leaveApplication);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        leaveApplication.setBarberName(loginUser.getUser().getUserName());
        leaveApplication.setBarberId(loginUser.getUser().getId().toString());
        leaveApplication.setReason(EmojiParser.parseToUnicode(leaveApplication.getReason()));
        leaveApplication.setStatus("0");
        //生成用户id
        UUID uuid = UUID.randomUUID();
        //前8位
        leaveApplication.setId((long) uuid.hashCode() & 0xFFFFFFFFL);
        int i = leaveApplicationMapper.insert(leaveApplication);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult cancleApplication(Long id) {
        LeaveApplication leaveApplication = leaveApplicationMapper.selectById(id);
        //设置状态码4 为取消申请
        leaveApplication.setStatus("3");
        int i = leaveApplicationMapper.updateById(leaveApplication);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }
}
