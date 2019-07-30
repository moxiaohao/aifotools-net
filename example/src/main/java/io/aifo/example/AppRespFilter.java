package io.aifo.example;

import com.foryou.net.filter.RespFilter;
import com.foryou.net.filter.data.RespData;
import com.foryou.net.filter.data.RespError;

/**
 * Description:
 * Created by moxiaohao
 * Date: 10/1/2019
 * Email: shenlei@foryou56.com
 */
public class AppRespFilter implements RespFilter {

    @Override
    public RespData filter(RespChain chain) throws Exception {

        RespData respData = chain.respData();
//        if (null == respData.respError) {
//            BaseEntity entity = (BaseEntity) respData.respEntity.entity();
//            if(entity.code == 0){
//                respData.respError = new RespError(100,"脑子坏了");
//            }
//        }
        return respData;
    }
}
