package com.foryou.net.filter;

import com.foryou.net.filter.data.RespData;

/**
 * Description:
 * Created by moxiaohao
 * Date: 10/1/2019
 * Email: shenlei@foryou56.com
 */
public class RespBaseFilter implements RespFilter {

    @Override
    public RespData filter(RespChain chain) throws Exception {

        RespData respData = chain.respData();
        RespData dealRespData = chain.proceed(respData);
        if (dealRespData != null) {
            respData = dealRespData;
        }
        return respData;
    }

}
