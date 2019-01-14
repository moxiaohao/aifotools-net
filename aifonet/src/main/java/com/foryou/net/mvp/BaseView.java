package com.foryou.net.mvp;

import android.content.Context;
import com.foryou.net.rx.FoYoLifeCycle;

/**
 * Description:
 * Created by moxiaohao
 * Date: 7/1/2019
 * Email: shenlei@foryou56.com
 */
public interface BaseView<T> extends FoYoLifeCycle {

    /**t
     * Presenter 获取view 的 Context
     *
     * @return
     */
    Context getViewContext();

}
