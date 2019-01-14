package com.foryou.net.callback;

/**
 * Description:
 * Created by moxiaohao
 * Date: 29/11/2018
 * Email: shenlei@foryou56.com
 */
public interface IFailure {

    /**
     * @param code request error code
     * @param desc request error description
     */
    void onFailure(int code, String desc);

}
