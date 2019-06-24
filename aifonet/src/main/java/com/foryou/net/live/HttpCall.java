package com.foryou.net.live;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import com.foryou.net.filter.data.RespData;


/**
 * Description:
 * Created by shenlei
 * Date:2019-06-24 10:27
 * Email:shenlei@foryou56.com
 */
public abstract class HttpCall<ResultType> {

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    public HttpCall() {

        LiveData<RespData<ResultType>> respDataLiveData = liveMethod();

        result.addSource(respDataLiveData, data -> {

            Resource<ResultType> tResource;
            if (null != data) {
                if (data.respError == null) {
                    tResource = new Resource<>(Status.SUCCESS, data.respEntity.entity());
                } else {
                    tResource = new Resource<>(Status.ERROR, data.respError.code(), data.respEntity.entity(), data.respError.errorMsg());
                }
                result.setValue(tResource);
            }
        });
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    public abstract LiveData<RespData<ResultType>> liveMethod();


}
