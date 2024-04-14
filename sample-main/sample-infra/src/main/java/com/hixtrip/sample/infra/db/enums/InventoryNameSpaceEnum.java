package com.hixtrip.sample.infra.db.enums;

import lombok.Getter;

@Getter
public enum InventoryNameSpaceEnum {

    SELLABLE("sell:sellable", "可售库存"),
    WITHHOLDING("sell:withholding", "预占库存"),
    OCCUPIED("sell:occupied", "占用库存");

    private String value;
    private String label;


    private InventoryNameSpaceEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getRedisNameSpace(String skuId) {
        return String.format("%s:%s", value, skuId);
    }
}
