package com.flyingcow.fastgo.server.service;

import com.flyingcow.fastgo.server.dto.RoleAuthorityDTO;
import com.flyingcow.fastgo.server.entity.SysAuthorityEntity;

import java.util.List;


/**
 * 
 * 
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2018-04-12 11:00:55
 */
public interface SysAuthorityService extends BaseService<SysAuthorityEntity,Integer>{
    List<RoleAuthorityDTO> getAuthesByRoleId(Integer var1);

}
