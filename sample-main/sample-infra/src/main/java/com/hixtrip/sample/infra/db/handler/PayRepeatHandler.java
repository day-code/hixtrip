package com.hixtrip.sample.infra.db.handler;

import com.hixtrip.sample.infra.db.enums.PayStatusEnum;
import org.springframework.stereotype.Component;

@Component
public class PayRepeatHandler implements PayHandler {

    @Override
    public PayStatusEnum getHandlerType() {
        return PayStatusEnum.REPEAT;
    }

    @Override
    public void handler(String skuId, Integer quantity) {
        //to do 重复支付操作
    }
}