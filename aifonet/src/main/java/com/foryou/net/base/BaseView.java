package com.foryou.net.base;

import android.content.Context;
import com.foryou.net.rx.FoYoLifeCycle;

/**
 * Description:
 * Created by moxiaohao
 * Date: 7/1/2019
 * Email: shenlei@foryou56.com
 */
public interface BaseView extends FoYoLifeCycle {

    Context getViewContext();

}
