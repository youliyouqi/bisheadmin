package com.example.barbershopsystem.service.impl;

import com.example.barbershopsystem.domain.ResponseResult;
import com.example.barbershopsystem.domain.User;
import com.example.barbershopsystem.domain.UserBalance;
import com.example.barbershopsystem.enums.AppHttpCodeEnum;
import com.example.barbershopsystem.mapper.RegisterMapper;
import com.example.barbershopsystem.mapper.UserBalanceMapper;
import com.example.barbershopsystem.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @author 尤里尤气
 * Created on 2024/4/8-18:52
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private UserBalanceMapper userBalanceMapper;

@Autowired
private RegisterMapper registerMapper;
    @Override
    @Transactional
    public ResponseResult register(User user) {

        //生成用户id
        UUID uuid = UUID.randomUUID();
        // 将UUID转换为字符串并移除短划线
        String uuidString = uuid.toString().replace("-", "");

        // 截取前5个字符
        String firstFiveChars = uuidString.substring(0, 5);

        user.setId((long) firstFiveChars.hashCode() & 0xFFFFFFFFL);
        //生成用户随机昵称
        user.setNickName("用户"+uuid.toString().substring(0,4));
        //设置状态 0 正常 1停用
        user.setStatus(String.valueOf(0));
        //设置用户类型 1 管理员 2理发师 3 用户
        user.setUserType(String.valueOf(3));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        System.out.println(user);
        //保存
        int insert = registerMapper.insert(user);
        if (insert== 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.REGISTER_ERROR);
        }
        //默认金额为0
        UserBalance userBalance = new UserBalance();
        userBalance.setUserId(user.getId());
        userBalance.setBalance(0.0);
        userBalanceMapper.insert(userBalance);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }
}
