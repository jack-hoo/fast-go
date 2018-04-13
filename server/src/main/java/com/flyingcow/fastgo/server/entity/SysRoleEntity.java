package com.flyingcow.fastgo.server.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigDecimal;



/**
 * 
 * 
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2018-04-12 11:11:45
 */
@Data
@Accessors(chain = true)
public class SysRoleEntity implements Serializable,GrantedAuthority {
	private static final long serialVersionUID = 1L;
	
	        //
        private Integer id;
	
	        //角色代码名称
        private String roleName;
	
	        //角色描述
        private String roleDesc;


	@Override
	public String getAuthority() {
		return this.roleName;
	}
}
