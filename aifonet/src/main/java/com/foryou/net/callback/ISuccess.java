package com.foryou.net.callback;


/**
 * Description:
 * Created by moxiaohao
 * Date: 29/11/2018
 * Email: shenlei@foryou56.com
 */
public interface ISuccess<T> {

    /**
     * request success callback method
     *
     * @param response request success data
     */
    void onSuccess(T response);

}
