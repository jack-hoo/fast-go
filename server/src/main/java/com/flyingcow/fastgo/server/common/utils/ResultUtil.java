/**
 * Copyright (C), 2008-2017, 杭州迪火科技有限公司
 * FileName: ResultUtil
 * Author:   shugan
 * Date:     2017/12/15 19:20
 * Description: 返回数据处理工具类
 */
package com.flyingcow.fastgo.server.common.utils;

import com.flyingcow.fastgo.server.common.constant.ExceptionEnum;

/**
 * 〈返回数据处理工具类〉
 *
 * @author shugan
 * @create 2017/12/15
 * @since 1.0.0
 */
public class ResultUtil<T> {

    /**
     * 返回成功，传入返回体具体出參
     * @param object
     * @return
     */
    public static <T> Result success(T object){
        Result result = new Result();
        result.setStatus(0);
        result.setMsg("success");
        result.setData(object);
        return result;
    }

    /**
     * 提供给部分不需要出參的接口
     * @return
     */
    public static Result success(){
        return success(null);
    }

    /**
     * 自定义错误信息
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setStatus(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static Result error(Integer code, String msg, Object data) {
        Result result = new Result();
        result.setStatus(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    /**
     * 返回异常信息，在已知的范围内
     * @param exceptionEnum
     * @return
     */
    public static Result error(ExceptionEnum exceptionEnum){
        Result result = new Result();
        result.setStatus(exceptionEnum.getCode());
        result.setMsg(exceptionEnum.getMessage());
        result.setData(null);
        return result;
    }
}