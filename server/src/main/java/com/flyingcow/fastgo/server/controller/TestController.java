package com.flyingcow.fastgo.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: jack-hoo
 * @Date: 2018/4/11 00:03
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/")
public class TestController {
    protected static final Logger payLog = LoggerFactory.getLogger("payLog");

    @RequestMapping("")
    public String test() {
//        payLog.error("支付日志1");
        /*log.info("joijooj");
        log.error("错误日志");
        log.warn("⚠日志");*/
//        List<SysUser> list = sysUserService.queryList(new HashMap());
        return "ok";
    }
}
