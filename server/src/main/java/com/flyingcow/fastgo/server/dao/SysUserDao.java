package com.flyingcow.fastgo.server.dao;

import com.flyingcow.fastgo.server.entity.SysUserEntity;
import com.flyingcow.fastgo.server.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
 * 
 * 
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2018-04-12 11:11:45
 */
@Mapper
@Repository
public interface SysUserDao extends BaseDao<SysUserEntity> {
    SysUserEntity queryByAccount(String account);
}
