//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.flyingcow.fastgo.server.dto;

import javax.validation.constraints.NotNull;

public class ChangePwdDTO {
    @NotNull(
            message = "登录账号不能为空"
    )
    private String account;
    @NotNull(
            message = "原始密码不能为空"
    )
    private String oldPwd;
    @NotNull(
            message = "新密码不能为空"
    )
    private String newPwd;

    public ChangePwdDTO() {
    }

    public String getAccount() {
        return this.account;
    }

    public String getOldPwd() {
        return this.oldPwd;
    }

    public String getNewPwd() {
        return this.newPwd;
    }

    public ChangePwdDTO setAccount(String account) {
        this.account = account;
        return this;
    }

    public ChangePwdDTO setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
        return this;
    }

    public ChangePwdDTO setNewPwd(String newPwd) {
        this.newPwd = newPwd;
        return this;
    }

}
