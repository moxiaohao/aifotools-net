package com.foryou.net.filter;

import java.util.ArrayList;
import java.util.List;

import com.foryou.net.config.Configurator;
import com.foryou.net.config.ConfigKeys;
import com.foryou.net.filter.chain.RealRespChain;
import com.foryou.net.filter.data.RespData;

/**
 * Description:
 * Created by moxiaohao
 * Date: 10/1/2019
 * Email: shenlei@foryou56.com
 */
public class RespFilterManager {

    /**
     * 执行 网络请求 返回数据 的分类处理 解耦框架和业务层
     * @param data
     * @return
     * @throws Exception
     */
    public static RespData execute(RespData data) throws Exception {

        List<RespFilter> filters = new ArrayList<>();
        List<RespFilter> clientFilters = Configurator.getInstance().getConfiguration(ConfigKeys.NET_FILTER);

        filters.add(new RespBaseFilter());
        if (null != clientFilters && clientFilters.size() > 0) {
            filters.addAll(clientFilters);
        }
        RespFilter.RespChain realRespChain = new RealRespChain(filters, data, 0);
        return realRespChain.proceed(data);
    }


}
