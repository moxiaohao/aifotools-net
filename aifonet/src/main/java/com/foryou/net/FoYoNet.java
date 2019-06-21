package com.foryou.net;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;
import android.os.Handler;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import com.foryou.net.callback.IFailure;
import com.foryou.net.callback.ISuccess;
import com.foryou.net.config.Configurator;
import com.foryou.net.config.ConfigKeys;
import com.foryou.net.filter.data.RespData;
import com.foryou.net.http.IMethod;
import com.foryou.net.http.HttpCreator;
import com.foryou.net.http.LiveMethod;
import com.foryou.net.live.NetLiveBuilder;
import com.foryou.net.live.Resource;
import com.foryou.net.live.Status;
import com.foryou.net.loader.FoYoLoader;
import com.foryou.net.loader.LoaderStyle;
import com.foryou.net.rx.FoYoLifeCycle;
import com.foryou.net.rx.FoYoObserver;
import com.foryou.net.rx.SwitchSchedulers;
import com.foryou.net.utils.FoYoNetBuilder;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;

/**
 * Description: FoYo tools Appearance class
 * Created by moxiaohao
 * Date: 29/11/2018
 * Email: ls.m@foxmail.com
 */
public class FoYoNet {

    public static final String TAG = FoYoNet.class.getSimpleName();

    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    private final ISuccess<Object> SUCCESS;
    private final IFailure FAILURE;
    private IMethod FOYONETMETHOD;
    private LiveMethod LIVEMETHOD;
    private Class<?> SERVICE;
    private final FoYoLifeCycle LIFE_CYCLE;
    private Class<Observer> observerClass;

//    private final MediatorLiveData<Resource<>> result = new MediatorLiveData<>();


    public FoYoNet(String url,
                   Class<?> service,
                   IMethod method,
                   Map<String, Object> params,
                   RequestBody body,
                   Context context,
                   LoaderStyle loaderStyle,
                   File file,
                   ISuccess<Object> success,
                   IFailure failure,
                   FoYoLifeCycle lifecycle,
                   LiveMethod liveMethod
    ) {
        this.URL = url;
        this.SERVICE = service;
        this.FOYONETMETHOD = method;
        if (!PARAMS.isEmpty()) {
            PARAMS.clear();
        }
        PARAMS.putAll(params);
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.LIFE_CYCLE = lifecycle;
        this.LIVEMETHOD = liveMethod;

    }

    public FoYoNet(String url,
                   Class<?> service,
                   IMethod method,
                   Map<String, Object> params,
                   RequestBody body,
                   Context context,
                   LoaderStyle loaderStyle,
                   ISuccess<Object> success,
                   IFailure failure,
                   FoYoLifeCycle lifecycle
    ) {
        this.URL = url;
        this.SERVICE = service;
        this.FOYONETMETHOD = method;
        if (!PARAMS.isEmpty()) {
            PARAMS.clear();
        }
        PARAMS.putAll(params);
        this.BODY = body;
        this.CONTEXT = context;
        this.LOADER_STYLE = loaderStyle;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.LIFE_CYCLE = lifecycle;
    }

    public static FoYoNetBuilder builder() {
        return new FoYoNetBuilder();
    }

    public static NetLiveBuilder onLive() {
        return new NetLiveBuilder();
    }


    public static Configurator init(Context context) {
        Configurator.getInstance().getFoYoConfigs().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return FoYoNet.getConfigurator();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    /**
     * 可用来设置 全局变量
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getConfiguration(Object key) {
        return Configurator.getInstance().getConfiguration(key);
    }

    public static Context getApp() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    /**
     * 构造请求Observable 可在项目中RxJava 串行使用
     *
     * @param <T>
     * @return
     */
    public <T> Observable<T> request() throws Exception {

        Observable observable = null;
        if (null == FOYONETMETHOD) {
            throw new Exception("net method can not be null");
        }
        Object service;
        if (null != SERVICE) {
            service = HttpCreator.getService(SERVICE);
        } else {
            throw new Exception("Attention : service method parameters can not be null");
        }
        if (PARAMS.isEmpty()) {
            throw new Exception("params can not be null");
        }
        observable = FOYONETMETHOD.ob(service, PARAMS);
        if (null == observable) {
            throw new Exception("observable can not be null");
        }
        return observable;
    }


    /**
     * 构造请求Observable 可在项目中RxJava 串行使用
     *
     * @param <T>
     * @return
     */
    public <T> LiveData<Resource<T>> asLiveData() {

        MediatorLiveData<Resource<T>> result = new MediatorLiveData<>();
        assert (null == LIVEMETHOD) : "Attention : live method can not be null";
        assert (null == SERVICE) : "Attention : service method parameters can not be null";
        Object service = HttpCreator.getService(SERVICE);

        result.addSource(LIVEMETHOD.ob(service, PARAMS), data -> {
            RespData<T> respData = (RespData<T>) data;
            Resource<T> tResource;
            if (respData.respError == null) {
                tResource = new Resource<>(Status.SUCCESS, respData.respEntity.entity());
            } else {
                tResource = new Resource<>(Status.ERROR, respData.respError.code(), respData.respEntity.entity(), respData.respError.errorMsg());
            }
            result.setValue(tResource);
        });
        return result;
    }


    private interface CallListener<T> {

        void onSucc(T data);

        void onFail(int code, String desc);
    }

    /**
     * 调用次方法 执行具体的请求操作
     */
    public void excute() {
        try {
            showLoading();
            Observable o = request();
            if (null != o) {
                execute(o, new CallListener() {
                    @Override
                    public void onSucc(Object data) {
                        stopLoding();
                        if (null != SUCCESS) {
                            SUCCESS.onSuccess(data);
                        }
                    }

                    @Override
                    public void onFail(int code, String desc) {
                        stopLoding();
                        if (null != FAILURE) {
                            FAILURE.onFailure(code, desc);
                        }
                    }
                });
            } else {
                throw new Exception("Observable get failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            stopLoding();
        }
    }


    /**
     * 设置请求的具体回调监听
     *
     * @param observable
     * @param <T>
     */
    private <T> void execute(Observable<T> observable, final CallListener callBack) {
        Observable<T> tObservable = observable.compose(new SwitchSchedulers<T>().applySchedulers());
        if (null != LIFE_CYCLE) {
            tObservable = tObservable.compose(LIFE_CYCLE.<T>bindToLife());
        }
        tObservable.subscribe(new FoYoObserver<T>() {
            @Override
            public void onSuccess(T data) {
                callBack.onSucc(data);
            }

            @Override
            public void onFailure(int code, String desc) {
                callBack.onFail(code, desc);
            }
        });
    }

    private void showLoading() {
        if (null != CONTEXT && null != LOADER_STYLE) {
            FoYoLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
    }

    private void stopLoding() {
        if (null != LOADER_STYLE) {
            FoYoLoader.stopLoading();
        }
    }


}
