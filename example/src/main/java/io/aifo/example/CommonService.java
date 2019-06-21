package io.aifo.example;

import android.arch.lifecycle.LiveData;

import com.foryou.net.filter.data.RespData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommonService {

    //检查更新
    @FormUrlEncoded
    @POST("user/login")
    Observable<SingleEntity> login(@FieldMap Map<String, Object> params);

    //检查更新
    @FormUrlEncoded
    @POST("user/login")
    LiveData<RespData<SingleEntity>> update(@FieldMap Map<String, Object> params);
}
