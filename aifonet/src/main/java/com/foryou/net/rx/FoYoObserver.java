package com.foryou.net.rx;


import com.foryou.net.filter.RespFilterManager;
import com.foryou.net.filter.data.IRespEntity;
import com.foryou.net.filter.data.RespData;
import com.foryou.net.filter.data.RespEntity;
import com.foryou.net.filter.data.RespError;
import com.foryou.net.http.ErrorStatus;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Description: 网路请求数据返回处理的第一层
 * Created by moxiaohao
 * Date: 23/11/2018
 * Email: shenlei@foryou56.com
 */
public abstract class FoYoObserver<T> implements Observer<T> {

    private static final int OBSERVER_EXCEPTION = 700;

    public abstract void onSuccess(T data);

    public abstract void onFailure(int code, String desc);

    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(T t) {

        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        if (null == t) {
            preOnFailure(ErrorStatus.ErrorCode.CODE_PARSE_ERROR.code,
                    ErrorStatus.ErrorCode.CODE_PARSE_ERROR.name);
            return;
        }
        preOnSuccess(t);

    }
    @Override
    public void onError(Throwable e) {
        ErrorStatus es = ErrorStatus.getStatus(e);
        preOnFailure(es.code, es.msg);
    }

    @Override
    public void onComplete() {

    }

    private void preOnFailure(int code, String desc) {
        RespData proceedData;
        RespData newErrorData = new RespData();
        try {
            newErrorData.respError = new RespError(code, desc);
            proceedData = RespFilterManager.execute(newErrorData);
        } catch (Exception e) {
            proceedData = new RespData();
            proceedData.respError = new RespError(OBSERVER_EXCEPTION, e.getMessage());
            e.printStackTrace();
        }
        onFailure(proceedData.respError.code(), proceedData.respError.errorMsg());
    }

    @SuppressWarnings("unchecked")
    private void preOnSuccess(T t) {

        RespData successData = new RespData();
        try {
            successData.respEntity = new RespEntity<T>(t);

            RespData proceedData = RespFilterManager.execute(successData);
            if (proceedData.respError == null) {
                onSuccess((T) proceedData.respEntity.entity());
            } else {
                onFailure(proceedData.respError.code(), proceedData.respError.errorMsg());
            }
        } catch (Exception e) {
            successData.respError = new RespError(OBSERVER_EXCEPTION, e.getMessage());
            preOnFailure(successData.respError.code(), successData.respError.errorMsg());
            e.printStackTrace();
        }

    }


}
