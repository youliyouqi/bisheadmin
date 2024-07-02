package com.example.barbershopsystem.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 尤里尤气
 * Created on 2024/4/7-21:35
 */
@RestController
public class test {
    @RequestMapping("/index")
    @PreAuthorize("hasAuthority('system:user:login1')")
    public String index(){
        return "helloWord";
    }
}
