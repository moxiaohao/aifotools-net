package com.foryou.net.mvp;

import java.util.List;

/**
 * Description:
 * Created by moxiaohao
 * Date: 7/1/2019
 * Email: shenlei@foryou56.com
 */
public interface BaseBiz {

    interface LoadTasksCallback<T> {

        void onTasksLoaded(List<T> tasks);

        void onDataNotAvailable(int errorType, String message);
    }

    interface GetTaskCallback<T> {

        void onTaskLoaded(T task);

        void onDataNotAvailable(int errorType, String message);
    }

}
