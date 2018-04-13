//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.flyingcow.fastgo.server.common.utils;

public class CurrentUserWraper {
    private Integer id;
    private String account;
    private Integer roleId;

    public CurrentUserWraper() {
    }

    public Integer getId() {
        return this.id;
    }

    public String getAccount() {
        return this.account;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public CurrentUserWraper setId(Integer id) {
        this.id = id;
        return this;
    }

    public CurrentUserWraper setAccount(String account) {
        this.account = account;
        return this;
    }

    public CurrentUserWraper setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

}
