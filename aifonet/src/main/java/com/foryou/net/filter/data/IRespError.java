package com.foryou.net.filter.data;

/**
 * Description:
 * Created by moxiaohao
 * Date: 10/1/2019
 * Email: shenlei@foryou56.com
 */
public interface IRespError<T> {

    int code();

    String errorMsg();

    void setCode(int code);

    void setErrorMsg(String errorMsg);

    T t();

    void setT(T t);


}
