package com.example.barbershopsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.example.barbershopsystem.mapper")
@EnableScheduling
public class BarberShopSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BarberShopSystemApplication.class, args);
    }

}
