/**
 * Copyright (C), 2008-2018, 杭州迪火科技有限公司
 * FileName: RoleConstant
 * Author:   shugan
 * Date:     2018/2/25 15:19
 * Description: 角色枚举
 */
package com.flyingcow.fastgo.server.common.constant;

/**
 * 〈角色枚举〉
 *
 * @author shugan
 * @create 2018/2/25
 * @since 1.0.0
 */
public enum RoleConstant {
    ADMIN(1,"ADMIN"),
    VIP_USER(2,"VIP_USER")
    ;
    private int roleId;
    private String roleName;

    RoleConstant(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
