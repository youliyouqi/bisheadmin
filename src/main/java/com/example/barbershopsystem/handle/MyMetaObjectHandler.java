package com.example.barbershopsystem.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.barbershopsystem.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
            userId = -1L;// 表示是自己创建
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Date date = Date.from(zonedDateTime.toInstant());

        this.setFieldValByName("createTime", date, metaObject);
        this.setFieldValByName("createtime", date, metaObject);
        System.out.println("插入的时间为"+date);
        this.setFieldValByName("createBy",userId , metaObject);
        this.setFieldValByName("updateTime", date, metaObject);
        this.setFieldValByName("updateBy", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName(" ", SecurityUtils.getUserId(), metaObject);
    }
}