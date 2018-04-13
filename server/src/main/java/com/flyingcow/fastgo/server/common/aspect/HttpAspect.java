/**
 * Copyright (C), 2008-2017, 杭州迪火科技有限公司
 * FileName: HttpAspect
 * Author:   shugan
 * Date:     2017/12/15 19:52
 * Description: 请求记录切面
 */
package com.flyingcow.fastgo.server.common.aspect;

import com.flyingcow.fastgo.server.common.exception.GlobalExceptionHandler;
import com.flyingcow.fastgo.server.common.utils.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈请求记录切面〉
 *
 * @author shugan
 * @create 2017/12/15
 * @since 1.0.0
 */
@Aspect
@Component
public class HttpAspect {

    private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);
    @Autowired
    private GlobalExceptionHandler exceptionHandler;
    @Pointcut("execution(public * com..miner.out.yinliu.con.*.*(..))")
    //@Pointcut("execution(public * com.miner.out.yinliu.controller.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url

        logger.info("url={}", request.getRequestURL());
        //method
        logger.info("method={}", request.getMethod());
        //ip
        logger.info("id={}", request.getRemoteAddr());
        //class_method
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "," + joinPoint.getSignature().getName());
        //args[]
        logger.info("args={}", joinPoint.getArgs());
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Result result = null;
        if (result == null) {
            return proceedingJoinPoint.proceed();
        } else {
            return result;
        }
    }

    @AfterReturning(pointcut = "log()",returning = "object")
    public void doAfterReturing(Object object) {
        logger.info("response={}",object.toString());
    }
}