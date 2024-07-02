package com.example.barbershopsystem.config;

import com.example.barbershopsystem.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//开启权限校验注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
//密码加密
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //将AuthenticationManager暴露
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
                .antMatchers("/").anonymous()
                .antMatchers("/user/register").anonymous()
                .antMatchers("/uploadImg").anonymous()
                .antMatchers("/announcement/getInfoList").anonymous()
                .antMatchers("/announcement/getInfoListByTypeName").anonymous()
                .antMatchers("/announcementclassification/getAnnounInfoCLass").anonymous()
                .antMatchers("/CarouselImageManagement/getImg").anonymous()
                .antMatchers("/announcement/getAnnounInfoById/*").anonymous()
                .antMatchers("/announcement/addClick").anonymous()
                .antMatchers("/announcement/getAnnounInfoByClickNum").anonymous()
                .antMatchers("/announcement/getAnnounInfoBycreateTime").anonymous()
                .antMatchers("/systemintro/getInfo/*").anonymous()
                .antMatchers("/systemintro/getInfo").anonymous()
                .antMatchers("/aboutus/getInfo/*").anonymous()
                .antMatchers("/aboutus/getInfo").anonymous()
                .antMatchers("/hairstyle/getInfoList").anonymous()
                .antMatchers("/hairstyle/likeSelect").anonymous()
                .antMatchers("/hairstyle/getNames").anonymous()
                .antMatchers("/haircutProject/getInfoList").anonymous()
                .antMatchers("/haircutProject/likeSelect").anonymous()
                .antMatchers("/projectCategory/getNames").anonymous()
                .antMatchers("/haircutProject/getInfoByCategoryId").anonymous()
                .antMatchers("/haircutProject/getInfoById/*").anonymous()
                .antMatchers("/discountActivities/getInfoList").anonymous()
                .antMatchers("/discountActivities/getInfoById/*").anonymous()
                .antMatchers("/discountActivities/likeSelect").anonymous()
                .antMatchers("/user/getInfoById/*").anonymous()
                .antMatchers("/user/getInfoListByBarber").anonymous()
                .antMatchers("/user//validatePassword/*").anonymous()
                .antMatchers("/user/restPass").anonymous()
                .antMatchers("/userBalance/getMoney/*").anonymous()
                .antMatchers("/reviews/getInfoListByBarberId").anonymous()
                .antMatchers("/orders//getBaberStatus/*").anonymous()
                .antMatchers("/announcement/likeSelect").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();


        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
       //添加异常处理
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
        //允许跨域
        http.cors();
    }
}