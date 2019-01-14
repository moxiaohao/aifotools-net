package com.foryou.net.filter.data;

import java.io.Serializable;

/**
 * Description:
 * Created by moxiaohao
 * Date: 10/1/2019
 * Email: shenlei@foryou56.com
 */
public class RespError<T> implements IRespError, Serializable {

    private int code;
    private String errorMsg;
    private T t;

    public RespError(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public RespError(int code, String errorMsg, T t) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.t = t;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String errorMsg() {
        return errorMsg;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public Object t() {
        return t;
    }

    @Override
    public void setT(Object o) {
        this.t = (T) o;
    }


}
