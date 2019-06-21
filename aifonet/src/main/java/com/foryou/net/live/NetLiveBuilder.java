package com.foryou.net.live;

import android.content.Context;
import android.text.TextUtils;

import com.foryou.net.FoYoNet;
import com.foryou.net.callback.IFailure;
import com.foryou.net.callback.ISuccess;
import com.foryou.net.config.ConfigKeys;
import com.foryou.net.config.Configurator;
import com.foryou.net.http.IMethod;
import com.foryou.net.http.LiveMethod;
import com.foryou.net.loader.LoaderStyle;
import com.foryou.net.rx.FoYoLifeCycle;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Description:
 * Created by moxiaohao
 * Date: 7/1/2019
 * Email: shenlei@foryou56.com
 */
public class NetLiveBuilder {

    private String mUrl = null;
    private Map<String, Object> PARAMS = new WeakHashMap<>();
    private RequestBody mRequestBody = null;
    private Context mContext = null;
    private LoaderStyle mLoaderStyle = null;
    private File mFile = null;
    private ISuccess mSuccess = null;
    private IFailure mFailure = null;
    private IMethod mMethod;
    private Class<?> mService;
    private FoYoLifeCycle mFoYoRxLifecycle;
    private Call mCall;
    private LiveMethod mLiveMethod ;

    public NetLiveBuilder() {
    }

    public final NetLiveBuilder service(Class<?> service) {
        this.mService = service;
        return this;
    }

    public final NetLiveBuilder params(Map<String, String> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final NetLiveBuilder params(String key, String value) {
        if (null != value)
            PARAMS.put(key, value);
        return this;
    }

    public final NetLiveBuilder params(String key, Object value) {
        if (null != value)
            PARAMS.put(key, value);
        return this;
    }

    public final NetLiveBuilder paramsValueObj(Map<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final NetLiveBuilder createCall(Call call) {
        this.mCall = call;
        return this;
    }

    public final NetLiveBuilder liveMethod(LiveMethod liveMethod) {
        this.mLiveMethod = liveMethod;
        return this;
    }



    public final NetLiveBuilder raw(String raw) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final NetLiveBuilder context(Context context) {
        this.mContext = context;
        return this;
    }

    public final NetLiveBuilder bindLifeCycle(FoYoLifeCycle lifeCycle) {
        this.mFoYoRxLifecycle = lifeCycle;
        return this;
    }

    public final NetLiveBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final NetLiveBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final FoYoNet build() {
        HashMap<String, Object> globalParams = Configurator.getInstance().getConfiguration(ConfigKeys.NET_GLOBLE_PARAMS);
        if (null != globalParams) {
            PARAMS.putAll(globalParams);
        }
        String token = Configurator.getInstance().getConfiguration(ConfigKeys.TOKEN);
        if (!TextUtils.isEmpty(token)) {
            PARAMS.put("token", token);
        }
        return new FoYoNet(mUrl, mService,
                mMethod, PARAMS, mRequestBody,
                mContext, mLoaderStyle, mFile, mSuccess,
                mFailure, mFoYoRxLifecycle, mLiveMethod);
    }

}
