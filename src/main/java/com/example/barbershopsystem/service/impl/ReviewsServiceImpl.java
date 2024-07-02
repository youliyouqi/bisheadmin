package com.example.barbershopsystem.service.impl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.barbershopsystem.domain.*;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.ReviewsMapper;
import com.example.barbershopsystem.mapper.UserMapper;
import com.example.barbershopsystem.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

/**
 * (Reviews)表服务实现类
 *
 * @author makejava
 * @since 2024-04-24 21:01:23
 */
@Service("reviewsService")
public class ReviewsServiceImpl extends ServiceImpl<ReviewsMapper, Reviews> implements ReviewsService {
    @Autowired
    private ReviewsMapper reviewsMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult getInfoList(int pageNum, int pageSize) {
        Page<Reviews> page = new Page<>(pageNum,pageSize);
        reviewsMapper.selectPage(page,null);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoById(Long id) {
        LambdaQueryWrapper<Reviews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Reviews::getOrderid,id);
        Reviews reviews = reviewsMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(reviews)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(reviews);
    }

    @Override
    public ResponseResult score(Reviews reviews) {
        System.out.println("评分"+reviews);
        reviews.setStatus(1);
        int i = reviewsMapper.updateById(reviews);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public ResponseResult getInfoListByUserId(int pageNum, int pageSize, String userId) {
        LambdaQueryWrapper<Reviews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Reviews::getUserid,userId);
        Page<Reviews> page = new Page<>(pageNum,pageSize);
        reviewsMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoListByBarberId(int pageNum, int pageSize, String barberId) {
        LambdaQueryWrapper<Reviews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Reviews::getBarberid,barberId);
        Page<Reviews> page = new Page<>(pageNum,pageSize);
        reviewsMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult fuzzySearch(int pageNum, int pageSize, String name) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication+"logout");
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userType = loginUser.getUser().getUserType();
        if (userType.equals("2")){
            LambdaQueryWrapper<Reviews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Reviews::getBarberid,loginUser.getUser().getId());
            lambdaQueryWrapper.like(Reviews::getId,name);
            Page<Reviews> page = new Page<>(pageNum,pageSize);
            reviewsMapper.selectPage(page,lambdaQueryWrapper);
            return ResponseResult.okResult(page);
        }
        LambdaQueryWrapper<Reviews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Reviews::getId,name);
        Page<Reviews> page = new Page<>(pageNum,pageSize);
        reviewsMapper.selectPage(page,lambdaQueryWrapper);
        return ResponseResult.okResult(page);
    }

    @Override
    public ResponseResult getInfoById1(Long id) {
        Reviews reviews = reviewsMapper.selectById(id);
        if (Objects.isNull(reviews)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NOT_FIND_ERROR);
        }
        return ResponseResult.okResult(reviews);
    }

    @Override
    public ResponseResult deleteById(Long id) {
        int i = reviewsMapper.deleteById(id);
        if (i<=0){return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);}
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseResult getBonus(Long id) {
        LocalDate firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime startOfMonth = LocalDateTime.of(firstDayOfMonth, LocalTime.MIN);
        LocalDateTime endOfMonth = LocalDateTime.of(lastDayOfMonth, LocalTime.MAX);
        LambdaQueryWrapper<Reviews> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Reviews::getBarberid,id);
        lambdaQueryWrapper.between(Reviews::getCreatetime, startOfMonth, endOfMonth);
        List<Reviews> reviews = reviewsMapper.selectList(lambdaQueryWrapper);
        Double bonusCommission = 0.0;
        if (!reviews.isEmpty()){
            for (Reviews review : reviews) {
                Integer rating = review.getRating();
                if (rating == 5){
                    bonusCommission+=5;
                }else if (rating == 4){
                    bonusCommission+=3;
                }else if (rating == 3){
                    bonusCommission+=0;
                }else if (rating == 2){
                    bonusCommission+=-1;
                }else {
                    bonusCommission+= -3;
                }
            }
            System.out.println("奖金为"+bonusCommission);
            BarberBonsVo barberBonsVo =  new BarberBonsVo();
            barberBonsVo.setBarberid(reviews.get(0).getBarberid());
            barberBonsVo.setBarbername(reviews.get(0).getBarbername());
            barberBonsVo.setBonusCommission(bonusCommission);
            return ResponseResult.okResult(barberBonsVo);
        }
        BarberBonsVo barberBonsVo =  new BarberBonsVo();
        User user = userMapper.selectById(id);
        barberBonsVo.setBonusCommission(bonusCommission);
        barberBonsVo.setBarberid(user.getId());
        barberBonsVo.setBarbername(user.getUserName());
       return ResponseResult.okResult(barberBonsVo);

    }
}
