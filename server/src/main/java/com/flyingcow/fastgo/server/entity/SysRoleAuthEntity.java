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
public class SysRoleAuthEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;

    //角色对应id
    private Integer roleId;

    //权限对应id
    private Integer authId;


}
