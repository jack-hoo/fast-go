/**
 * Copyright (C), 2008-2017, 杭州迪火科技有限公司
 * FileName: ArgumentInvalidResult
 * Author:   shugan
 * Date:     2017/12/15 21:28
 * Description: 参数验证
 */
package com.flyingcow.fastgo.server.common.utils;

/**
 * 〈参数验证〉
 *
 * @author shugan
 * @create 2017/12/15
 * @since 1.0.0
 */
public class ArgumentInvalidResult {
    private String field;
    private Object rejectedValue;
    private String errorMessage;

    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public Object getRejectedValue() {
        return rejectedValue;
    }
    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
