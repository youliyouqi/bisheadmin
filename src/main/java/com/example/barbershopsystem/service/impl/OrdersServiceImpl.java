package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.*;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.*;
import com.example.barbershopsystem.service.OrdersService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

/**
 * (Orders)表服务实现类
 *
 * @author makejava
 *
 *
 *
 */
@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private ReviewsMapper reviewsMapper;

    @Autowired
    private UserBalanceMapper  userBalanceMapper;

    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;
@Autowired
private HaircutProjectMapper haircutProjectMapper;
    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication+"logout");
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userType = loginUser.getUser().getUserType();
        if (userType.equals("2")){
            LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Orders::getBarberid,loginUser.getUser().getId());
            Page<Orders> page = new Page<>(pageNum,pageSize);
            ordersMapper.selectPage(page,lambdaQueryWrapper);
            return ResponseResult.okResult(page);
        }
        Page<Orders> page = new Page<>(pageNum,pageSize);
        ordersMapper.selectPage(page,null);
        return ResponseResult.okResult(page);
    }

    @Override
    @Transactional
    public ResponseResult approval(Orders orders) {
        System.out.println(orders+"审核传来的订单");
        Orders ordersInfo = ordersMapper.selectById(orders.getId());

        if (orders.getStatus()==2){
            //通过审核 ，添加到评价
           Reviews reviews = new Reviews();
            // 0 未完成评价  1 完成服务
            reviews.setStatus(0);
            reviews.setPicture(ordersInfo.getPicture());
            reviews.setBarberid(ordersInfo.getBarberid());
            reviews.setBarbername(ordersInfo.getBarbername());
            reviews.setProjectid(ordersInfo.getProjectid());
            reviews.setProjectname(ordersInfo.getProjectname());
            reviews.setUserid(ordersInfo.getUserid());
            reviews.setUsername(ordersInfo.getUsername());
            reviews.setProjectprice(ordersInfo.getProjectprice());
            reviews.setOrderid(ordersInfo.getId());
            reviewsMapper.insert(reviews);


        }
         ordersInfo.setStatus(orders.getStatus());


        int i = ordersMapper.updateById(ordersInfo);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        //进行用户扣款
        LambdaQueryWrapper<UserBalance> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserBalance::getUserId,ordersInfo.getUserid());
        UserBalance userBalance = userBalanceMapper.selectOne(lambdaQueryWrapper);
        userBalance.setBalance(userBalance.getBalance()-ordersInfo.getProjectprice());
        int i1 = userBalanceMapper.updateById(userBalance);
        if (i1<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}

        //添加消费记录
        RechargeRecord rechargeRecord = new RechargeRecord();
        //生成用户id
        UUID uuid = UUID.randomUUID();
        //前8位
        rechargeRecord.setId((long) uuid.hashCode() & 0xFFFFFFFFL);
        rechargeRecord.setPrice(ordersInfo.getProjectprice());
        rechargeRecord.setOperatorId(ordersInfo.getUserid());
        rechargeRecord.setRechargeName(ordersInfo.getProjectname());
        //1 未到店消费  2已完成服务
        rechargeRecord.setTransactionType(String.valueOf(ordersInfo.getStatus()));
        rechargeRecord.setRechargePersonId(ordersInfo.getUserid());
        rechargeRecord.setRechargeTypeId(ordersInfo.getProjectid());
        rechargeRecord.setPaymentStatus("0");
        rechargeRecord.setRechargeTime(ordersInfo.getCreatetime());
        int insert = rechargeRecordMapper.insert(rechargeRecord);
        if (insert<=0){return ResponseResult.errorResult(AppHttpCodeEnum.ADD_ERROR);}

        //项目人数回复
        HaircutProject haircutProject = haircutProjectMapper.selectById(ordersInfo.getProjectid());
        haircutProject.setMaxCapacity(haircutProject.getMaxCapacity()+1);
        int i2 = haircutProjectMapper.updateById(haircutProject);
        if (i2<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication+"logout");
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userType = loginUser.getUser().getUserType();
        if (userType.equals("2")){
            LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Orders::getBarberid,loginUser.getUser().getId());
            lambdaQueryWrapper.like(Orders::getId,name);
            Page<Orders> page = new Page<>(pageNum,pageSize);
            ordersMapper.selectPage(page,lambdaQueryWrapper);
            return ResponseResult.okResult(page);
        }
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Orders::getId,name);
        Page<Orders> page = new Page<>(pageNum,pageSize);
        ordersMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getAInfoById(Long id) {
        Orders orders = ordersMapper.selectById(id);
        if (Objects.isNull(orders)){return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);}
        return ResponseResult.okResult(orders);
    }

    @Override
    public ResponseResult getInfoListByUserId(int pageNum, int pageSize, String userId) {
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getUserid,userId);
        Page<Orders> page = new Page<>(pageNum,pageSize);
        ordersMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getBaberStatus(Long id) {
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getBarberid,id);
        Long count = ordersMapper.selectCount(lambdaQueryWrapper);
        return ResponseResult.okResult(count);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = ordersMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getInfoListByBarberId(int pageNum, int pageSize, String barberId) {
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getBarberid,barberId);
        Page<Orders> page = new Page<>(pageNum,pageSize);
        ordersMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }
}
