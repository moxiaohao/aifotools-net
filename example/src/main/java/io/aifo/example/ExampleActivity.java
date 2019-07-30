package io.aifo.example;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.foryou.net.FoYoNet;
import com.foryou.net.base.GetTaskFail;
import com.foryou.net.base.GetTaskSucc;
import com.foryou.net.callback.ISuccess;
import com.foryou.net.filter.data.RespData;
import com.foryou.net.http.HttpCreator;
import com.foryou.net.http.IMethod;
import com.foryou.net.live.HttpCall;
import com.foryou.net.live.Resource;
import com.foryou.net.rx.FoYoLifeCycle;
import com.foryou.net.rx.FoYoObserver;
import com.foryou.net.utils.FoYoLogger;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
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

//                request().observe(ExampleActivity.this, singleEntityResource -> {
//                    if (null != singleEntityResource) {
//                        if (singleEntityResource.status == Status.ERROR) {
//                            Log.i(TAG, "onClick: ERROR" + ":Code:" + singleEntityResource.code + "message:" + singleEntityResource.message);
//                        } else if (singleEntityResource.status == Status.SUCCESS) {
//                            Log.i(TAG, "onClick: SUCCESS" + ":Code:" + singleEntityResource.code + "Data:" + singleEntityResource.data.toString());
//                        }
//                    }
//                });
//                Intent intent = new Intent(ExampleActivity.this, SecondActivity.class);
//                startActivity(intent);

                for (int i = 0; i < 500; i++) {
                    request(task -> {
                    }, (code, desc) -> {
                    });
                    System.out.println("----------------->"+i);
                }
            }
        });
    }


    LiveData<Resource<SingleEntity>> request() {
        return new HttpCall<SingleEntity>() {
            @Override
            public LiveData<RespData<SingleEntity>> liveMethod() {
                HashMap<String, Object> params = new HashMap<>();
                return HttpCreator.getService(CommonService.class).update(params);
            }

        }.asLiveData();
    }


    void request(GetTaskSucc succ, GetTaskFail fail) {

        FoYoNet.builder()
                .params("username", "jake") //添加参数
                .params("password", "123456")
                .bindLifeCycle(this) //绑定生命周期 处理内存泄露
                .service(CommonService.class)  //Retrofit 请求 Service类
                .method((IMethod<CommonService>) CommonService::login) //具体的请求 方法
                .success((ISuccess<SingleEntity>) response -> {
                    succ.onTaskLoaded(response);
                    FoYoLogger.i(TAG, "request success and do something");//请求成功 更新数据
                })
                .failure((code, desc) -> {
                    fail.onTaskLoadedFail(code, desc);
                    FoYoLogger.i(TAG, "request failed and do something----->"+desc); //请求失败 提示原因
                })
                .build()//构建
                .excute();//执行
    }


    @SuppressLint("CheckResult")
    void requestLink() {

        Observable.just("orderSn").flatMap(new Function<String, Observable<SingleEntity>>() {
            @Override
            public Observable<SingleEntity> apply(String orderSn) throws Exception {
                return FoYoNet.builder()
                        .params("orderSn", orderSn)
                        .service(CommonService.class)
                        .method((IMethod<CommonService>) CommonService::login)
                        .build()
                        .request();
            }
        }).flatMap(new Function<SingleEntity, Observable<SingleEntity>>() {
            @Override
            public Observable<SingleEntity> apply(SingleEntity makePointInfoEntity) throws Exception {
                return FoYoNet.builder()
                        .service(CommonService.class)
                        .method((IMethod<CommonService>) CommonService::login)
                        .build().request();
            }
        }).flatMap(new Function<SingleEntity, Observable<SingleEntity>>() {
            @Override
            public Observable<SingleEntity> apply(SingleEntity truckDetailEntity) throws Exception {
                return FoYoNet.builder()
                        .service(CommonService.class)
                        .method((IMethod<CommonService>) CommonService::login)
                        .build().request();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FoYoObserver<SingleEntity>() {
                    @Override
                    public void onSuccess(SingleEntity data) {
                        FoYoLogger.i(TAG, "request success and do something");//请求成功 更新数据
                    }

                    @Override
                    public void onFailure(int code, String desc) {
                        FoYoLogger.i(TAG, "request failed and do something"); //请求失败 提示原因
                    }
                });

    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }
}
