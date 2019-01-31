package com.foryou.net.config;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.ArrayList;
import java.util.HashMap;

import com.foryou.net.BuildConfig;
import com.foryou.net.filter.RespFilter;

import okhttp3.Interceptor;

/**
 * Description: Global configuration class
 * Created by moxiaohao
 * Date: 29/11/2018
 * Email: shenlei@foryou56.com
 */
public final class Configurator {

    private static final HashMap<Object, Object> FOYO_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final ArrayList<RespFilter> FILTERS = new ArrayList<>();

    private Configurator() {
        FOYO_CONFIGS.put(ConfigKeys.CONFIG_READY, false);
        FOYO_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final public HashMap<Object, Object> getFoYoConfigs() {
        return FOYO_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        FOYO_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
    }

    public final Configurator withLoggerAdapter() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Skips some method invokes in stack trace. Default 5
//              .logStrategy(logStrategy) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("MainActivity")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return Configurator.isDebugMode();
            }
        });
        return this;
    }

    public final Configurator withDebugMode(boolean debug) {
        FOYO_CONFIGS.put(ConfigKeys.DEBUG_MODE, debug);
        return this;
    }

    public final Configurator withApiHost(String host) {
        FOYO_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final Configurator withToken(String token) {
        FOYO_CONFIGS.put(ConfigKeys.TOKEN, token);
        return this;
    }

    public final Configurator withHttpsCerPath(String path) {
        FOYO_CONFIGS.put(ConfigKeys.HTTPS_CER, path);
        return this;
    }

    public final Configurator withLoaderDelayed(long delayed) {
        FOYO_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        FOYO_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        FOYO_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withRespFilter(RespFilter respFilter) {
        FILTERS.add(respFilter);
        FOYO_CONFIGS.put(ConfigKeys.NET_FILTER, FILTERS);
        return this;
    }

    public final Configurator withRespFilters(ArrayList<RespFilter> respFilters) {
        FILTERS.addAll(respFilters);
        FOYO_CONFIGS.put(ConfigKeys.NET_FILTER, FILTERS);
        return this;
    }

    public Configurator withJavascriptInterface(@NonNull String name) {
        FOYO_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    public Configurator withNetHeaders(HashMap<String, String> headers) {
        FOYO_CONFIGS.put(ConfigKeys.NET_HEADERS, headers);
        return this;
    }

    public Configurator withNetGlobleParams(HashMap<String, Object> params) {
        FOYO_CONFIGS.put(ConfigKeys.NET_GLOBLE_PARAMS, params);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) FOYO_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    public final boolean hasKey(Object key) {
        checkConfiguration();
        return FOYO_CONFIGS.containsValue(key);
    }

    public static boolean isDebugMode() {
        Object configuration = Configurator.getInstance().getConfiguration(ConfigKeys.DEBUG_MODE);
        boolean isDebug = false;
        if (null != configuration) {
            isDebug = (boolean) configuration;
        }
        return isDebug;
    }

    final public <T> T getConfiguration(Object key) {
        checkConfiguration();
//        if (!FOYO_CONFIGS.containsKey(key)) {
//            throw new NullPointerException(" KEY " + key.toString() + "  IS NULL");
//        }
//        final Object value = FOYO_CONFIGS.get(key);
//        if (value == null) {
//            throw new NullPointerException(" VALUE" + key.toString() + " IS NULL");
//        }
        return (T) FOYO_CONFIGS.get(key);
    }
}
