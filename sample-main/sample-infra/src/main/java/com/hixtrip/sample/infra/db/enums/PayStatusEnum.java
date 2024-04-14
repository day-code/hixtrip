package com.hixtrip.sample.infra.db.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {

    SUCCESS("1", "支付成功"), FAIL("0", "支付失败"), REPEAT("3", "重复支付");
    private String value;
    private String label;

    PayStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
