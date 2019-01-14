package com.foryou.net.filter;
import java.io.IOException;
import com.foryou.net.filter.data.RespData;

/**
 * Description:
 * Created by moxiaohao
 * Date: 10/1/2019
 * Email: shenlei@foryou56.com
 */
public interface RespFilter {

    RespData filter(RespChain chain) throws Exception;

    interface RespChain {

        RespData proceed(RespData data) throws Exception;

        RespData respData();
    }

}
