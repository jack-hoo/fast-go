package com.flyingcow.fastgo.server.service.impl;

import com.flyingcow.fastgo.server.dto.RoleAuthorityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flyingcow.fastgo.server.dao.SysAuthorityDao;
import com.flyingcow.fastgo.server.entity.SysAuthorityEntity;
import com.flyingcow.fastgo.server.service.SysAuthorityService;

import java.util.List;


@Service("sysAuthorityService")
public class SysAuthorityServiceImpl extends BaseServiceImpl<SysAuthorityEntity, Integer> implements SysAuthorityService {
	@Autowired
	private SysAuthorityDao sysAuthorityDao;


	@Override
	public List<RoleAuthorityDTO> getAuthesByRoleId(Integer var1) {
		return sysAuthorityDao.findAuthsByRoleId(var1);
	}
}
