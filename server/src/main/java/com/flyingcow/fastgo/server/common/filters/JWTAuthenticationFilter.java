/**
 * Copyright (C), 2008-2017, 杭州迪火科技有限公司
 * FileName: JWTAuthenticationFilter
 * Author:   shugan
 * Date:     2017/12/17 13:20
 * Description: 认证过滤器
 */
package com.flyingcow.fastgo.server.common.filters;

import com.alibaba.fastjson.JSON;
import com.flyingcow.fastgo.server.common.exception.BizException;
import com.flyingcow.fastgo.server.common.utils.JwtTokenUtil;
import com.flyingcow.fastgo.server.common.utils.Result;
import com.flyingcow.fastgo.server.common.utils.UserDetailsUtil;
import com.flyingcow.fastgo.server.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 〈认证过滤器〉
 *
 * @author shugan
 * @create 2017/12/17
 * @since 1.0.0
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SysUserServiceImpl userDetailsService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.header}")
    private String header;
    @Value("${jwt.secret}")
    private String secret;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestHeader = request.getHeader(this.header);
        if (requestHeader != null && requestHeader.startsWith(this.tokenHead)) {
            UsernamePasswordAuthenticationToken authentication = null;

            try {
                authentication = this.getAuthentication(request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            } catch (BizException var11) {
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(200);
                Result result = new Result();
                result.setStatus(var11.getCode());
                result.setMsg(var11.getMessage());
                result.setData(new Object());
                String errorInfo = JSON.toJSONString(result);
                OutputStream out = response.getOutputStream();
                out.write(errorInfo.getBytes());
                out.flush();
                out.close();
            }

        } else {
            chain.doFilter(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(this.header);
        if (token != null) {
            String authToken = token.substring(this.tokenHead.length());
            String username = this.jwtTokenUtil.getUsernameFromToken(authToken);
            UserDetailsUtil userDetails = (UserDetailsUtil)this.userDetailsService.loadUserByUsername(username);
            return userDetails != null ? new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities()) : null;
        } else {
            return null;
        }
    }
}