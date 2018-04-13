/**
 * Copyright (C), 2008-2017, 杭州迪火科技有限公司
 * FileName: BizException
 * Author:   shugan
 * Date:     2017/12/15 19:25
 * Description: 统一异常处理
 */
package com.flyingcow.fastgo.server.common.exception;

import com.flyingcow.fastgo.server.common.constant.ExceptionEnum;

/**
 * 〈统一异常处理〉
 *
 * @author shugan
 * @create 2017/12/15
 * @since 1.0.0
 */
public class BizException extends RuntimeException {
    private int code;

    public BizException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
    }
    /**
     * 自定义错误信息
     * @param message
     * @param code
     */
    public BizException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}