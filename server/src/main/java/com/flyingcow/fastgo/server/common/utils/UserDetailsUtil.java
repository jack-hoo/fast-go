/**
 * Copyright (C), 2008-2018, 杭州迪火科技有限公司
 * FileName: UserDetailsUtil
 * Author:   shugan
 * Date:     2018/2/25 12:59
 * Description: springsecurity中用户细节实现类
 */
package com.flyingcow.fastgo.server.common.utils;

import com.flyingcow.fastgo.server.common.constant.AccountStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 〈springsecurity中用户细节实现类〉
 *
 * @author shugan
 * @create 2018/2/25
 * @since 1.0.0
 */
@Data()
@Accessors(chain = true)
public class UserDetailsUtil implements UserDetails {
    //登录账号
    private String account;
    //登录密码
    private String pwd;
    //账号状态
    private AccountStatus status;

    public void setStatus(Integer code) {
        for (AccountStatus status : AccountStatus.values()) {
            if (code == status.getCode()) {
                this.status = status;
            }
        }
    }

    //权限集合
    private Collection<? extends GrantedAuthority> auths;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.auths;
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status != AccountStatus.EXPIRED_ACCOUNT;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status != AccountStatus.LOCKED_ACCOUNT;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status != AccountStatus.CREDENTIALS_EXPIRED_ACCOUNT;
    }

    @Override
    public boolean isEnabled() {
        return this.status == AccountStatus.ENABLED_ACCOUNT;
    }

}