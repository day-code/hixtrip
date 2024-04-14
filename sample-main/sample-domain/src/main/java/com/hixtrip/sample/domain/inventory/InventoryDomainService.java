package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
public class InventoryDomainService {

    @Resource
    InventoryRepository inventoryRepository;


    /**
     * 获取sku当前库存
     *
     * @param skuId
     */
    public Integer getInventory(String skuId) {
        //todo 需要你在infra实现，只需要实现缓存操作, 返回的领域对象自行定义
        return inventoryRepository.getInventory(skuId);
    }

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存：可以售卖的库存量
     * @param withholdingQuantity 预占库存：是指已经被系统调度临时占用的库存数量
     * @param occupiedQuantity    占用库存：已生成出货单的库存
     * @return
     */
    public Boolean changeInventory(String skuId, Integer sellableQuantity, Integer withholdingQuantity, Integer occupiedQuantity) {
        //todo 需要你在infra实现，只需要实现缓存操作。
        Inventory inventory = new Inventory(skuId, sellableQuantity, withholdingQuantity, occupiedQuantity);
        return inventoryRepository.changeInventory(inventory);
    }

    public Boolean sellProduct(String skuId, int quantity) {
        return inventoryRepository.sellProduct(skuId, quantity);
    }

    public Boolean changeQuantity(String skuId, int quantity) {
        return inventoryRepository.changeQuantity("sell:sellable:"+skuId,"sell:occupied:"+skuId, quantity);
    }
}
