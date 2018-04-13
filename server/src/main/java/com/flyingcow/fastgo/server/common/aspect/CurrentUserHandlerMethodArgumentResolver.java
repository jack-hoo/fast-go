//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.flyingcow.fastgo.server.common.aspect;

import com.flyingcow.fastgo.server.common.annotation.CurrentUser;
import com.flyingcow.fastgo.server.common.utils.CurrentUserWraper;
import com.flyingcow.fastgo.server.entity.SysUserEntity;
import com.flyingcow.fastgo.server.service.SysUserService;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

@Component
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    private SysUserService sysUserService;

    public CurrentUserHandlerMethodArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            String account = authentication.getName();
            CurrentUserWraper currentUser = new CurrentUserWraper();
            currentUser.setAccount(account);
            SysUserEntity sysUserEntity = this.sysUserService.findUserByAccount(account);
            if (sysUserEntity != null) {
                currentUser.setId(sysUserEntity.getId());
                currentUser.setRoleId(sysUserEntity.getRoleId());
            }

            return currentUser;
        } else {
            return null;
        }
    }
}
