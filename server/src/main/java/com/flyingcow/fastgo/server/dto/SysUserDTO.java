//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.flyingcow.fastgo.server.dto;

import com.flyingcow.fastgo.server.common.validator.group.Register;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SysUserDTO {
    @NotNull(
            message = "登录账号不能为空",
            groups = {Register.class}
    )
    private String account;
    @NotNull(
            message = "登录密码不能为空",
            groups = {Register.class}
    )
    @Pattern(
            regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$",
            message = "密码由6-16位数字和字母的组合"
    )
    private String password;
    private String username;
    private Integer sex;
    private String email;
    private String phone;
    private String wechatId;
    private String qq;

    public SysUserDTO() {
    }

    public String getAccount() {
        return this.account;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public Integer getSex() {
        return this.sex;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getWechatId() {
        return this.wechatId;
    }

    public String getQq() {
        return this.qq;
    }

    public SysUserDTO setAccount(String account) {
        this.account = account;
        return this;
    }

    public SysUserDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public SysUserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public SysUserDTO setSex(Integer sex) {
        this.sex = sex;
        return this;
    }

    public SysUserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public SysUserDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public SysUserDTO setWechatId(String wechatId) {
        this.wechatId = wechatId;
        return this;
    }

    public SysUserDTO setQq(String qq) {
        this.qq = qq;
        return this;
    }

}
