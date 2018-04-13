/**
 * Copyright (C), 2008-2017, 杭州迪火科技有限公司
 * FileName: RestAuthEntryPoint
 * Author:   shugan
 * Date:     2017/12/18 16:59
 * Description: 自定义spring security 认证入口点
 */
package com.flyingcow.fastgo.server.common.filters;

import com.alibaba.fastjson.JSON;
import com.flyingcow.fastgo.server.common.utils.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 〈自定义spring security 认证入口点〉
 *
 * @author shugan
 * @create 2017/12/18
 * @since 1.0.0
 */
@Component
public class RestAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        Result result = new Result();
        result.setStatus(400003);
        result.setMsg("请求未授权");
        result.setData(new Object());
        String errorInfo = JSON.toJSONString(result);
        OutputStream out = response.getOutputStream();
        out.write(errorInfo.getBytes());
        out.flush();
    }
}