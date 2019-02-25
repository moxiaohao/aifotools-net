package io.aifo.example;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.foryou.net.FoYoNet;
import com.foryou.net.callback.ISuccess;
import com.foryou.net.filter.data.RespData;
import com.foryou.net.http.IMethod;
import com.foryou.net.rx.FoYoLifeCycle;
import com.foryou.net.rx.FoYoObserver;
import com.foryou.net.utils.FoYoLogger;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class ExampleActivity extends RxAppCompatActivity implements FoYoLifeCycle {

    public static final String TAG = ExampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
    }


    void request() {

        FoYoNet.builder()
                .params("username", "jake") //添加参数
                .params("password", "123456")
                .loader(ExampleActivity.this) //添加默认加载状态
                .bindLifeCycle(this) //绑定生命周期 处理内存泄露
                .service(CommonService.class)  //Retrofit 请求 Service类
                .method((IMethod<CommonService>) CommonService::login) //具体的请求 方法
                .success((ISuccess<SingleEntity>) response -> {
                    FoYoLogger.i(TAG, "request success and do something");//请求成功 更新数据
                })
                .failure((code, desc) -> {
                    FoYoLogger.i(TAG, "request failed and do something"); //请求失败 提示原因
                })
                .build()//构建
                .excute();//执行
    }

    @SuppressLint("CheckResult")
    void requestLink() {

        Observable.just("").flatMap(s -> FoYoNet.builder()
                .params("username", "jake") //添加参数
                .params("password", "123456")
                .loader(ExampleActivity.this) //添加默认加载状态
                .service(CommonService.class)  //Retrofit 请求 Service类
                .method((IMethod<CommonService>) CommonService::login) //具体的请求 方法;
                .build().request()).flatMap(o -> FoYoNet.builder()
                .params("username", "jake") //添加参数
                .params("password", "123456")
                .loader(ExampleActivity.this) //添加默认加载状态
                .service(CommonService.class)  //Retrofit 请求 Service类
                .method((IMethod<CommonService>) CommonService::login) //具体的请求 方法;
                .build().request())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FoYoObserver<Object>() {
                    @Override
                    public void onSuccess(RespData data) {
                        FoYoLogger.i(TAG, "request success and do something");//请求成功 更新数据
                    }
                    @Override
                    public void onFailure(RespData data) {
                        FoYoLogger.i(TAG, "request failed and do something"); //请求失败 提示原因
                    }
                });

    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }
}
