/**
 * Copyright (C), 2008-2018, 杭州迪火科技有限公司
 * FileName: AccountStatus
 * Author:   shugan
 * Date:     2018/2/25 13:04
 * Description: 用户账号状态(锁定，正常，过期，)
 */
package com.flyingcow.fastgo.server.common.constant;

/**
 * 〈用户账号状态(锁定，正常，密码过期，账号过期)〉
 *
 * @author shugan
 * @create 2018/2/25
 * @since 1.0.0
 */
public enum AccountStatus {
    ENABLED_ACCOUNT(0,"正常可用"),
    LOCKED_ACCOUNT(1,"账号被锁定"),
    EXPIRED_ACCOUNT(2,"账号过期"),
    CREDENTIALS_EXPIRED_ACCOUNT(3,"密码过期"),
    INACTIVED_ACCOUNT(4,"账号未激活")
    ;

    AccountStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    private int code;
    private String status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}