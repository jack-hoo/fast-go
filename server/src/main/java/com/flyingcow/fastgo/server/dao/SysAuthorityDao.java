package com.flyingcow.fastgo.server.dao;

import com.flyingcow.fastgo.server.dto.RoleAuthorityDTO;
import com.flyingcow.fastgo.server.entity.SysAuthorityEntity;
import com.flyingcow.fastgo.server.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2018-04-12 11:00:55
 */
@Mapper
@Repository
public interface SysAuthorityDao extends BaseDao<SysAuthorityEntity> {
    List<RoleAuthorityDTO> findAuthsByRoleId(Integer var1);

}

