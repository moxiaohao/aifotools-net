package com.foryou.net.live;

/**
 * Description:
 * Created by shenlei
 * Date:2019-06-20 14:08
 * Email:shenlei@foryou56.com
 */
@FunctionalInterface
public interface DataLoad<T> {
    T load();
}
