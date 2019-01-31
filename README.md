# aifotools
Android Development Tools

# 框架介绍
- 基于OkHttp Retrofit RxJava 用构建者模式封装的网络库 

## 功能特点
- 初始化方便 使用方便
- 支持Https配置
- 支持请求日志格式化
- 支持返回类型自主配置
- 支持MVP生命周期管理
- 支持配合RxJava链式请求
- 支持配置全局Token
- 支持全局参数配置

# 安装说明：

## Step 1. Add the JitPack repository to your build file

#### Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

## Step 2. Add the dependency
```
dependencies 
{
	implementation 'com.github.moxiaohao:aifotools:Tag'
}
```

# 使用说明:

- add this code in your Application class
```
FoYoNet.init(application)  //初始化
                .withApiHost(UrlConstant.URL_BASE)  //设置网络请求 同理的 Domain
                .withHttpsCerPath("fuyoukache.cer") //设置Https 证书
                .withRespFilter(new FyResponseFilter()) //设置网络请求返回值 解析Filter
                .withInterceptor(new FoYoInterceptor()) //设置请求日志解析拦截器
                .withInterceptor(new ErrorLogInterceptor()) //设置请求错误处理拦截器
                .withToken(BaseSharePreferenceUtils.getToken()) //这只全局请求token
                .withNetGlobleParams(ApnInit.getHeads())   //这只全局请求参数
                .withLoggerAdapter()  //设置LogAdapter
                .withDebugMode(true)  //设置是否打印请求 日志
                .configure();  //配置生效
```



- define a Retrofit Service 
```
public interface CommonService {

    @FormUrlEncoded
    @POST("user/login")
    Observable<SingleEntity> login(@FieldMap WeakHashMap<String, Object> params);

}
```

- define a Result Entity for response json 

- BaseEntity
```
public class BaseEntity implements Serializable {

    public int code;
    public String desc;

}
```
- Business Entity
```
public class SingleEntity extends BaseEntity {

    public List<DataBean> data;
    public static class DataBean {
        public String title;
        public String content;
        public String hint;
        public List<String> infoList;
    }
}
```

- Activity or Fragment can implement FoYoLifeCycle
```
public class ExampleActivity extends RxAppCompatActivity implements FoYoLifeCycle
```
```
@Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }
```

- then you can send a http request 

```
          FoYoNet.builder()
                .params("username", "jake") //添加参数
                .params("password", "123456")
                .loader(ExampleActivity.this) //添加默认加载状态
                .bindLifeCycle(this) //绑定生命周期 处理内存泄露
                .service(CommonService.class)  //Retrofit 请求 Service类
                .method((IMethod<CommonService>)CommonService::login) //具体的请求 方法
                .success((ISuccess<SingleEntity>) response -> {
                    FoYoLogger.i(TAG, "request success and do something");//请求成功 更新数据
                })
                .failure((code, desc) -> {
                    FoYoLogger.i(TAG, "request failed and do something"); //请求失败 提示原因
                })
                .build()//构建
                .excute();//执行
```

- if you want use RxJava send a Request link  

```java
Observable.just("").flatMap(s -> FoYoNet.builder()
                .params("username", "jake") //添加参数
                .params("password", "123456")
                .loader(ExampleActivity.this) //添加默认加载状态
                .service(CommonService.class)  //Retrofit 请求 Service类
                .method((IMethod<CommonService>)CommonService::login) //具体的请求 方法;
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

```







