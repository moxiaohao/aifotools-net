package com.foryou.net.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import com.foryou.net.FoYoNet;
import com.foryou.net.callback.IFailure;
import com.foryou.net.callback.ISuccess;
import com.foryou.net.config.ConfigKeys;
import com.foryou.net.config.Configurator;
import com.foryou.net.http.IMethod;
import com.foryou.net.loader.LoaderStyle;
import com.foryou.net.mvp.BaseView;
import com.foryou.net.rx.FoYoLifeCycle;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Description:
 * Created by moxiaohao
 * Date: 7/1/2019
 * Email: shenlei@foryou56.com
 */
public class FoYoNetBuilder {

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
    private BaseView mView;

    public FoYoNetBuilder() {
    }

    public final FoYoNetBuilder service(Class<?> service) {
        this.mService = service;
        return this;
    }

    public final FoYoNetBuilder method(IMethod method) {
        this.mMethod = method;
        return this;
    }

    public final FoYoNetBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final FoYoNetBuilder params(Map<String, String> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final FoYoNetBuilder params(String key, String value) {
        if (null != value)
            PARAMS.put(key, value);
        return this;
    }

    public final FoYoNetBuilder params(String key, Object value) {
        if (null != value)
            PARAMS.put(key, value);
        return this;
    }

    public final FoYoNetBuilder paramsValueObj(Map<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final FoYoNetBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final FoYoNetBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final FoYoNetBuilder raw(String raw) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final FoYoNetBuilder context(Context context) {
        this.mContext = context;
        return this;
    }

    public final FoYoNetBuilder bindLifeCycle(FoYoLifeCycle lifeCycle) {
        this.mFoYoRxLifecycle = lifeCycle;
        return this;
    }

    public final FoYoNetBuilder view(BaseView view) {
        this.mView = view;
        this.mContext = view.getViewContext();
        this.mFoYoRxLifecycle = view;
        return this;
    }

    public final FoYoNetBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    public final FoYoNetBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    public final FoYoNetBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final FoYoNetBuilder failure(IFailure failure) {
        this.mFailure = failure;
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
                mFailure, mFoYoRxLifecycle);
    }

}
