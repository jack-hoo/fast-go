package com.flyingcow.fastgo.server.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


/**
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2018-04-12 11:11:45
 */
@Data
@Accessors(chain = true)
public class SysUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;

    //用户账号(只能是字母或者特殊字符)
    private String account;

    //密码(符合一定规则)
    private String password;

    //用户名称
    private String username;

    //性别:0 不公开，1 男，2 女
    private Integer sex;

    //邮箱
    private String email;

    //
    private String phone;

    //qq
    private String qq;

    //微信号
    private String wechatId;

    //角色id
    private Integer roleId;

    //账号状态：0正常，1,"账号被锁定"，2,"账号过期"，3,"密码过期"，4,"账号未激活"
    private Integer status;


}
