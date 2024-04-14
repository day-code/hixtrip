package com.hixtrip.sample.infra.db.factory;

import com.hixtrip.sample.infra.db.enums.PayStatusEnum;
import com.hixtrip.sample.infra.db.handler.PayHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目类型工厂
 */
@Component
public class PayHandlerFactory implements InitializingBean {
    @Resource
    private List<PayHandler> handlerList;

    private Map<PayStatusEnum, PayHandler> handlerMap = new HashMap<>();

    public String triggerPayHandler(PayStatusEnum payStatusEnum, String skuId, Integer quantity) {
        handlerMap.get(payStatusEnum).handler(skuId, quantity);
        return payStatusEnum.getLabel();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (PayHandler payHandler : handlerList) {
            handlerMap.put(payHandler.getHandlerType(), payHandler);
        }
    }
}
