package io.aifo.example;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CommonService {

    //检查更新
    @FormUrlEncoded
    @POST("user/login")
    Observable<SingleEntiry> login(@FieldMap WeakHashMap<String, Object> params);

}
