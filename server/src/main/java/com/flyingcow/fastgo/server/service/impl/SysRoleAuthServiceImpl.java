package com.flyingcow.fastgo.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flyingcow.fastgo.server.dao.SysRoleAuthDao;
import com.flyingcow.fastgo.server.entity.SysRoleAuthEntity;
import com.flyingcow.fastgo.server.service.SysRoleAuthService;



@Service("sysRoleAuthService")
public class SysRoleAuthServiceImpl extends BaseServiceImpl<SysRoleAuthEntity, Integer> implements SysRoleAuthService {
	@Autowired
	private SysRoleAuthDao sysRoleAuthDao;
	

}
