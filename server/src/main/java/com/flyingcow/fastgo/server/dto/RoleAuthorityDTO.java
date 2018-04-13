//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.flyingcow.fastgo.server.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;
@Data
@Accessors(chain = true)
public class RoleAuthorityDTO implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String authCode;
    private String authName;
    private String url;
    private String method;
    private String resourceType;
    private Long createTime;
    private Integer roleAuthId;


    @Override
    public String getAuthority() {
        return this.authCode;
    }
}
