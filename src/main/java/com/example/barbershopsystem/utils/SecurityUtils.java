package com.example.barbershopsystem.utils;

import com.example.barbershopsystem.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    /**
     * 获取当前登录用户
     **/
    public static LoginUser getLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof LoginUser)) {
            return null;
        }

        return (LoginUser) principal;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 检查当前用户是否为管理员
     */
    public static Boolean isAdmin() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null || loginUser.getUser() == null || loginUser.getUser().getId() == null) {
            return false;
        }

        Long id = loginUser.getUser().getId();
        return id.equals(1L);  // 这里假设管理员用户的ID为1
    }

    /**
     * 获取当前登录用户的ID
     */
    public static Long getUserId() {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null || loginUser.getUser() == null) {
            return null;
        }
        return loginUser.getUser().getId();
    }
}
