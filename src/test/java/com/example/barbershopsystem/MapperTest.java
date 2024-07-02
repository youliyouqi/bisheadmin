package com.example.barbershopsystem;

import com.example.barbershopsystem.domain.User;
import com.example.barbershopsystem.mapper.MenuMapper;
import com.example.barbershopsystem.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/**
 * @author 尤里尤气
 * Created on 2024/4/7-23:02
 */
@SpringBootTest
public class MapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void TestBCryptPasswordEncoder(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("admin");
        String encode2 = passwordEncoder.encode("1234");

        boolean matches = passwordEncoder.matches("1234",encode2);


        System.out.println(matches);
        System.out.println(encode);
        System.out.println(encode2);
    }

    @Test
    public void testSelectPermsByUserId(){
        List<String> list = menuMapper.selectPermsByUserId(1L);
        System.out.println(list);
    }
}
