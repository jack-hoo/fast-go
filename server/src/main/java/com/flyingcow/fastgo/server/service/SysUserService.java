package com.flyingcow.fastgo.server.service;

import com.flyingcow.fastgo.server.common.constant.AccountStatus;
import com.flyingcow.fastgo.server.dto.SysUserDTO;
import com.flyingcow.fastgo.server.entity.SysUserEntity;


/**
 * 
 * 
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2018-04-12 11:11:45
 */
public interface SysUserService extends BaseService<SysUserEntity,Integer>{
    String login(String var1, String var2);

    SysUserEntity findUserByAccount(String var1);

    SysUserEntity registerUser(SysUserDTO var1, int var2, AccountStatus var3);
}
