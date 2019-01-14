package com.foryou.net.rx;


import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Description:
 * Created by moxiaohao
 * Date: 23/11/2018
 * Email: shenlei@foryou56.com
 */
@FunctionalInterface
public interface FoYoLifeCycle {
    /**
     * 绑定生命周期
     */
    <T> LifecycleTransformer<T> bindToLife();
}
