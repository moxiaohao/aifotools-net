package com.foryou.net.filter.data;

import java.io.Serializable;

/**
 * Description:
 * Created by moxiaohao
 * Date: 10/1/2019
 * Email: shenlei@foryou56.com
 */
public class RespData<EntityType> implements Serializable {

    public IRespEntity<EntityType> respEntity;
    public IRespError<EntityType> respError;

}
