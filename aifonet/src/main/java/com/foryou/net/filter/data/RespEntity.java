package com.foryou.net.filter.data;

import java.io.Serializable;

/**
 * Description:
 * Created by moxiaohao
 * Date: 10/1/2019
 * Email: shenlei@foryou56.com
 */
public class RespEntity<T> implements IRespEntity, Serializable {

    T t;

    public RespEntity(T t) {
        this.t = t;
    }

    @Override
    public T entity() {
        return t;
    }

    @Override
    public void setEntity(Object o) {
        t = (T) o;
    }

}
