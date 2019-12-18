package com.kidd.shopping.utils;


import com.kidd.shopping.auth.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Util dùng để tạo các {@link GrantedAuthority} - chứa data về role của user, phục vụ cho authorization
 */
public class GrantedAuthorityUtils {

    /**
     * Lấy list các {@link GrantedAuthority} từ một đối tượng user
     * @param user user cần lấy list {@link GrantedAuthority}
     * @return List các {@link GrantedAuthority} của user
     */
    public static List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> result = new ArrayList<>();
        String role = user.getRole();
        result.add(new SimpleGrantedAuthority("ROLE_" + role));
        return result;
    }
}
