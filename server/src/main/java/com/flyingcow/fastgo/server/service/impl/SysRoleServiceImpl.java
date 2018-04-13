package com.flyingcow.fastgo.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flyingcow.fastgo.server.dao.SysRoleDao;
import com.flyingcow.fastgo.server.entity.SysRoleEntity;
import com.flyingcow.fastgo.server.service.SysRoleService;



@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleEntity, Integer> implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	

}
