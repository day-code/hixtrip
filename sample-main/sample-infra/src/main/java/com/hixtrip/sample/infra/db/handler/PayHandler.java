package com.hixtrip.sample.infra.db.handler;

import com.hixtrip.sample.infra.db.enums.PayStatusEnum;

public interface PayHandler {

    /**
     * 枚举身份的识别
     * @return
     */
    PayStatusEnum getHandlerType();

    /**
     * 支付回调处理
     * @param skuId
     */
    void handler(String skuId,Integer quantity);

}
