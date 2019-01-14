package com.foryou.net.http;

import java.util.WeakHashMap;

import io.reactivex.Observable;

/**
 * Description:
 * Created by moxiaohao
 * Date: 29/11/2018
 * Email: shenlei@foryou56.com
 */
@FunctionalInterface
public interface IMethod<X> {
    /**
     * 接口方法调用
     *
     * @param service 具体的Service类
     * @param params  request params
     * @return 返回的Observable<>对象W
     */
     Observable ob(X service, WeakHashMap params);
}
