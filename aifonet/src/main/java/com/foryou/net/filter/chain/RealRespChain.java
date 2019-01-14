package com.foryou.net.filter.chain;

import java.util.List;
import com.foryou.net.filter.RespFilter;
import com.foryou.net.filter.data.RespData;

/**
 * Description:
 * Created by moxiaohao
 * Date: 10/1/2019
 * Email: shenlei@foryou56.com
 */
public class RealRespChain implements RespFilter.RespChain {

    private List<RespFilter> filters;
    private RespData respData;
    private int index;

    public RealRespChain(List<RespFilter> filters, RespData respData, int index) {

        this.filters = filters;
        this.respData = respData;
        this.index = index;
    }

    @Override
    public RespData proceed(RespData data) throws Exception {

        RespFilter respFilter = filters.get(index);

        RealRespChain next = new RealRespChain(filters, respData, index + 1);

        RespData  respData = respFilter.filter(next);

        return respData;
    }

    @Override
    public RespData respData() {
        return respData;
    }


}
