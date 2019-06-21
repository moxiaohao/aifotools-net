package com.foryou.net.live;


import android.arch.lifecycle.LiveData;

import com.foryou.net.filter.RespFilterManager;
import com.foryou.net.filter.data.IRespError;
import com.foryou.net.filter.data.RespData;
import com.foryou.net.filter.data.RespEntity;
import com.foryou.net.filter.data.RespError;
import com.foryou.net.http.ErrorStatus;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Retrofit adapterthat converts the Call into a LiveData of ApiResponse.
 *
 * @param <R>
 */
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<RespData<R>>> {


    private static final int OBSERVER_EXCEPTION = 700;

    private final Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<RespData<R>> adapt(Call<R> call) {

        return new LiveData<RespData<R>>() {

            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            processResponse(response);
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable throwable) {
                            processFailure(throwable);
                        }
                    });
                }
            }

            private void processFailure(Throwable throwable) {
                ErrorStatus es = ErrorStatus.getStatus(throwable);
                RespData<R> rRespData = new RespData<>();
                rRespData.respEntity = null;
                rRespData.respError = new RespError<R>(es.code, es.msg);
                postValue(rRespData);
            }

            private void processResponse(Response<R> response) {

                RespData<R> successData = new RespData<>();
                RespEntity<Object> objectRespEntity = new RespEntity<>(response.body());
                objectRespEntity.setEntity(response.body());
                successData.respEntity = objectRespEntity;
                successData.respError = null;
                try {
                    RespData proceedData = RespFilterManager.execute(successData);
                    postValue(proceedData);
                } catch (Exception e) {
                    successData.respError = new RespError(OBSERVER_EXCEPTION, e.getMessage());
                    postValue(successData);
                    e.printStackTrace();
                }
            }
        };
    }


}
