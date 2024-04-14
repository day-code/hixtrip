package com.hixtrip.sample.infra.db.handler;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.db.enums.InventoryNameSpaceEnum;
import com.hixtrip.sample.infra.db.enums.PayStatusEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class PayFailHandler implements PayHandler {

    @Resource
    InventoryRepository inventoryRepository;

    @Override
    public PayStatusEnum getHandlerType() {
        return PayStatusEnum.FAIL;
    }

    @Override
    public void handler(String skuId, Integer quantity) {
        //预占库存减少，占用库存增加
        inventoryRepository.changeQuantity(InventoryNameSpaceEnum.WITHHOLDING.getRedisNameSpace(skuId), InventoryNameSpaceEnum.SELLABLE.getRedisNameSpace(skuId), quantity);
    }
}
