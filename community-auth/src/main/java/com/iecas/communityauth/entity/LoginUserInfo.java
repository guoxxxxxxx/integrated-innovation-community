/**
 * @Time: 2025/2/8 18:16
 * @Author: guoxun
 * @File: LoginUserInfo
 * @Description:
 */

package com.iecas.communityauth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



@Data
public class LoginUserInfo extends AuthUser implements UserDetails, CredentialsContainer {


    @Override
    public String getUsername() {
        return super.getUsername();
    }


    /**
     * 角色权限列表
     */
    @TableField(exist = false)
    private List<String> authorities;


    /**
     * 清除用户密码防止密码泄露
     */
    @Override
    public void eraseCredentials() {
        super.setPassword(null);
    }


    /**
     * 获取当前用户的权限列表
     * @return 当前用户的权限列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!authorities.isEmpty()) {
            List<GrantedAuthority> authorityList = new ArrayList<>();
            for (String authority : authorities) {
                authorityList.add(new SimpleGrantedAuthority(authority));
            }
            return authorityList;
        }
        else
            return List.of();
    }


    /**
     * 账户是否没有过期
     * @return true: no expired; false: expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return super.getAccountNonExpired() == 1;
    }


    /**
     * 账户是否未锁定
     * @return true: no locked; false: locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return super.getAccountNonLocked() == 1;
    }


    /**
     * 账户凭证是否过期
     * @return true: no expired; false: expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return super.getCredentialsNonExpired() == 1;
    }


    /**
     * 账户是否可用
     * @return true: enable; false: unable
     */
    @Override
    public boolean isEnabled() {
        return super.getEnabled() == 1;
    }
}
