/**
 * Copyright (C), 2008-2017, 杭州迪火科技有限公司
 * FileName: Result
 * Author:   shugan
 * Date:     2017/12/15 17:02
 * Description: 接口调用统一返回数据模型
 */
package com.flyingcow.fastgo.server.common.utils;

import java.io.Serializable;

/**
 * 〈接口调用统一返回数据模型〉
 *
 * @author shugan
 * @create 2017/12/15
 * @since 1.0.0
 */
public class Result<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    public Result(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Result(String msg, T data) {
        this.msg = msg;
        this.data = data;
        this.status = 0;
    }

    public Result(T data) {
        this.msg = "success";
        this.status = 0;
        this.data = data;
    }

    public Result() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}