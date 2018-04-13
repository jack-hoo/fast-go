package com.flyingcow.fastgo.server.controller;

import com.flyingcow.fastgo.server.common.constant.AccountStatus;
import com.flyingcow.fastgo.server.common.constant.ExceptionEnum;
import com.flyingcow.fastgo.server.common.exception.BizException;
import com.flyingcow.fastgo.server.common.utils.AddressUtils;
import com.flyingcow.fastgo.server.common.utils.Result;
import com.flyingcow.fastgo.server.common.utils.ResultUtil;
import com.flyingcow.fastgo.server.common.validator.group.Register;
import com.flyingcow.fastgo.server.dto.ChangePwdDTO;
import com.flyingcow.fastgo.server.dto.SysUserDTO;
import com.flyingcow.fastgo.server.entity.SysUserEntity;
import com.flyingcow.fastgo.server.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: jack-hoo
 * @Date: 2018/4/12 12:04
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private SysUserService userService;

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody @Validated({Register.class}) SysUserDTO user) {
        SysUserEntity sysUserEntity = this.userService.registerUser(user, 1, AccountStatus.ENABLED_ACCOUNT);
        return ResultUtil.success(sysUserEntity);
    }

    @PostMapping({"/login"})
    @ApiOperation(
            value = "用户登录",
            response = Result.class
    )
    public Result login(@RequestBody @Validated({Register.class}) SysUserDTO userDTO, HttpServletRequest request) {
        String token = "";
        SysUserEntity userByAccount = null;
        HashMap result = new HashMap();

        try {
            token = this.userService.login(userDTO.getAccount(), userDTO.getPassword());
            if (!StringUtils.isEmpty(token)) {
                userByAccount = this.userService.findUserByAccount(userDTO.getAccount());
                Map<String, Object> userInfo = new HashMap();
                userInfo.put("role", userByAccount.getRoleId());
                userInfo.put("name", userByAccount.getUsername());
                userInfo.put("account", userByAccount.getAccount());
                result.put("token", token);
                result.put("userInfo", userInfo);
            }
        } catch (AuthenticationException var8) {
            String ipAddr = AddressUtils.getIpAddr(request);
            log.error("{}尝试登录,ip地址为{}====区域为{}", new Object[]{userDTO.getAccount(), ipAddr, AddressUtils.getAddresses(ipAddr)});
            throw new BizException(var8.getMessage(), ExceptionEnum.LOGIN_ERR.getCode());
        }

        return ResultUtil.success(result);
    }

    @PostMapping({"/change_pwd"})
    @ApiOperation(
            value = "修改密码",
            response = Result.class
    )
    public Result changePassword(@RequestBody @Validated ChangePwdDTO changePwdDTO) {
        this.userService.login(changePwdDTO.getAccount(), changePwdDTO.getOldPwd());
        SysUserEntity user = this.userService.findUserByAccount(changePwdDTO.getAccount());
        user.setPassword(changePwdDTO.getNewPwd());
        this.userService.update(user);
        return ResultUtil.success();
    }
}
