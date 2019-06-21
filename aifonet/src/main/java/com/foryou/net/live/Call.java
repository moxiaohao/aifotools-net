package com.foryou.net.live;

import java.util.Map;

/**
 * Description:
 * Created by shenlei
 * Date:2019-06-20 14:07
 * Email:shenlei@foryou56.com
 */
@FunctionalInterface
public interface Call<RequestType> {
    RequestType create(Map<String, Object> params);
}
