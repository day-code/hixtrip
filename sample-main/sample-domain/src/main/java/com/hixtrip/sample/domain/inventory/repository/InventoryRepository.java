package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

/**
 *
 */
public interface InventoryRepository {

    /**
     * 获取sku当前库存
     *
     * @param skuId
     */
    Integer getInventory(String skuId);

    /**
     * 设置skuId在缓存中的库存数量
     * @param inventory
     * @return
     */
    Boolean changeInventory(Inventory inventory);

    /**
     * 在redis使用lua防止超卖，在缓存中可售减少、预占增加
     * @param skuId
     * @param quantity
     * @return
     */
    boolean sellProduct(String skuId,  Integer quantity);

    /**
     * 指定缓存的key增加和减少
     * @param decrbyKey
     * @param incrbyKey
     * @param quantity
     * @return
     */
    boolean changeQuantity(String decrbyKey, String incrbyKey, int quantity);
}
