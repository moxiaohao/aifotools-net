package com.foryou.net.http;

import android.arch.lifecycle.LiveData;

import com.foryou.net.filter.data.RespData;

import java.util.Map;

/**
 * Description:
 * Created by moxiaohao
 * Date: 29/11/2018
 * Email: shenlei@foryou56.com
 */
@FunctionalInterface
public interface LiveMethod<ServiceType, ResultType> {

    /**
     * 接口方法调用
     *
     * @param service 具体的Service类
     * @param params  request params
     * @return 返回的Observable<>对象W
     */
    LiveData<RespData<ResultType>> ob(ServiceType service, Map<String, Object> params);
}
