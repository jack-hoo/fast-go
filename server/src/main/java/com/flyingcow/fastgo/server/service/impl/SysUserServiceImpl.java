package com.flyingcow.fastgo.server.service.impl;

import com.flyingcow.fastgo.server.common.constant.AccountStatus;
import com.flyingcow.fastgo.server.common.constant.ExceptionEnum;
import com.flyingcow.fastgo.server.common.exception.BizException;
import com.flyingcow.fastgo.server.common.utils.JwtTokenUtil;
import com.flyingcow.fastgo.server.common.utils.UserDetailsUtil;
import com.flyingcow.fastgo.server.dao.SysAuthorityDao;
import com.flyingcow.fastgo.server.dao.SysRoleDao;
import com.flyingcow.fastgo.server.dto.RoleAuthorityDTO;
import com.flyingcow.fastgo.server.dto.SysUserDTO;
import com.flyingcow.fastgo.server.entity.SysRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.flyingcow.fastgo.server.dao.SysUserDao;
import com.flyingcow.fastgo.server.entity.SysUserEntity;
import com.flyingcow.fastgo.server.service.SysUserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("sysUserService")
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUserEntity, Integer> implements SysUserService,UserDetailsService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysAuthorityDao sysAuthorityDao;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public String login(String username, String password) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		String token = null;
		try {
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final UserDetailsUtil userDetails = (UserDetailsUtil) loadUserByUsername(username);
			token = jwtTokenUtil.generateToken(userDetails.getAccount());
			log.info("##用户##[{}]登录成功", username);
		} catch (BizException e) {
			log.error("##用户##[{}]登录失败,原因：{}",username,e.getMessage());
			throw new BizException(ExceptionEnum.LOGIN_ERR);
		} catch (AuthenticationException e) {
			log.error("##用户##[{}]登录失败,原因：{}", username, e.getMessage());
			throw new BizException(ExceptionEnum.LOGIN_ERR);
		}
		return token;
	}

	@Override
	public SysUserEntity findUserByAccount(String account) {
		return this.sysUserDao.queryByAccount(account);
	}

	@Override
	public SysUserEntity registerUser(SysUserDTO userDTO, int role, AccountStatus status) {
		SysUserEntity regUser = new SysUserEntity();
		BeanUtils.copyProperties(userDTO, regUser);
		regUser.setRoleId(role);
		regUser.setStatus(status.getCode());
		this.save(regUser);
		regUser.setPassword("登录密码为刚才输入的密码,由6-16位数字和字母组成");
		return regUser;
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		SysUserEntity user = sysUserDao.queryByAccount(s);
		if (user == null) {
			throw new BizException(ExceptionEnum.USER_NOT_EXIST);
		} else {
			SysRoleEntity sysRoleEntity = (SysRoleEntity)sysRoleDao.queryObject(user.getRoleId());
			List<RoleAuthorityDTO> auths = sysAuthorityDao.findAuthsByRoleId(user.getRoleId());
			List<GrantedAuthority> authorities = new ArrayList();
			authorities.add(sysRoleEntity);
			authorities.addAll(auths);
			UserDetailsUtil userDetails = new UserDetailsUtil();
			userDetails.setAccount(user.getAccount()).setPwd(user.getPassword()).setAuths(authorities).setStatus(user.getStatus());
			return userDetails;
		}
	}
	private SysUserEntity encodeUser(SysUserEntity sysUser) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPwd = sysUser.getPassword();
		sysUser.setPassword(encoder.encode(rawPwd));
		return sysUser;
	}

	@Override
	public void save(SysUserEntity sysUser) {
		sysUser = this.encodeUser(sysUser);
		this.sysUserDao.save(sysUser);
		log.info("系统用户注册成功:{}", sysUser.getUsername());
	}

	@Override
	public void update(SysUserEntity sysUser) {
		sysUser = this.encodeUser(sysUser);
		this.sysUserDao.update(sysUser);
		log.info("密码重置成功:{}", sysUser.getUsername());
	}
}
