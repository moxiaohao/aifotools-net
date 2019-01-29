package io.aifo.example;

import android.app.Application;

import com.foryou.net.FoYoNet;
import com.foryou.net.interceptors.FoYoInterceptor;

/**
 * Description:
 * Created by moxiaohao
 * Date: 7/1/2019
 * Email: shenlei@foryou56.com
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FoYoNet.init(this)
                .withApiHost("http://mock.fuyoukache.com/mock/5c36ed584e7ea94d5bf6ba3b/aifo/")
//              .withRespFilter(new AppRespFilter())
                .withInterceptor(new FoYoInterceptor())
                .withLoggerAdapter()
                .configure();

    }
}
