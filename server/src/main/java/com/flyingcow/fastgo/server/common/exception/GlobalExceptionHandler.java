/**
 * Copyright (C), 2008-2017, 杭州迪火科技有限公司
 * FileName: GlobalExceptionHandler
 * Author:   shugan
 * Date:     2017/12/15 19:48
 * Description: 全局异常处理器
 */
package com.flyingcow.fastgo.server.common.exception;

import com.flyingcow.fastgo.server.common.constant.ExceptionEnum;
import com.flyingcow.fastgo.server.common.utils.ArgumentInvalidResult;
import com.flyingcow.fastgo.server.common.utils.Result;
import com.flyingcow.fastgo.server.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 〈全局异常处理器〉
 *
 * @author shugan
 * @create 2017/12/15
 * @since 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Result methodArgumentNotValidHandler(HttpServletRequest request, MethodArgumentNotValidException exception) {
        List<ArgumentInvalidResult> invalidResults = new ArrayList();
        Iterator var4 = exception.getBindingResult().getFieldErrors().iterator();

        while(var4.hasNext()) {
            FieldError error = (FieldError)var4.next();
            ArgumentInvalidResult invalidArgument = new ArgumentInvalidResult();
            invalidArgument.setErrorMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setRejectedValue(error.getRejectedValue());
            invalidResults.add(invalidArgument);
        }

        return ResultUtil.error(ExceptionEnum.PARAMS_INVALID.getCode(), ExceptionEnum.PARAMS_INVALID.getMessage(), invalidResults);
    }

    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public Result requestTypeMismatch(TypeMismatchException e) {
        Map<String, String> map = new HashMap();
        map.put("值", e.getValue().toString());
        map.put("类型应为", e.getRequiredType().getName());
        return ResultUtil.error(ExceptionEnum.PARAMS_TYPE_ERR.getCode(), ExceptionEnum.PARAMS_TYPE_ERR.getMessage(), map);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public Result requestMissingServletRequest(MissingServletRequestParameterException e) {
        Map<String, String> map = new HashMap();
        map.put("参数名称", e.getParameterName());
        map.put("参数类型", e.getParameterType());
        return ResultUtil.error(ExceptionEnum.PARAMS_MISSING_ERR.getCode(), ExceptionEnum.PARAMS_MISSING_ERR.getMessage(), map);
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public Result accessDenied(AccessDeniedException e) {
        return ResultUtil.error(ExceptionEnum.UNAUTHORIZED_ERR.getCode(), ExceptionEnum.UNAUTHORIZED_ERR.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    @ResponseBody
    public Result UsernameNotFoundException(UsernameNotFoundException e) {
        return ResultUtil.error(ExceptionEnum.UNAUTHORIZED_ERR.getCode(), ExceptionEnum.UNAUTHORIZED_ERR.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result exceptionGet(Exception e) {
        if (e instanceof BizException) {
            BizException bizException = (BizException)e;
            return ResultUtil.error(bizException.getCode(), bizException.getMessage());
        } else {
            return ResultUtil.error(ExceptionEnum.UNKNOW_ERR);
        }
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public Result methodNotSupported(HttpRequestMethodNotSupportedException e) {
        return ResultUtil.error(ExceptionEnum.METHOD_NOT_SUPPORTED_ERR.getCode(), ExceptionEnum.METHOD_NOT_SUPPORTED_ERR.getMessage());
    }

    @ExceptionHandler({DuplicateKeyException.class})
    @ResponseBody
    public Result duplicateKeyException(DuplicateKeyException e) {
        return ResultUtil.error(ExceptionEnum.DATA_ALREADY_EXIST.getCode(), ExceptionEnum.DATA_ALREADY_EXIST.getMessage());
    }
}