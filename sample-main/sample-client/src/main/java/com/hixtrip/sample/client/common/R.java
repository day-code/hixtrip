package com.hixtrip.sample.client.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    static final Integer SUCCESS = 200;
    static final Integer FAIL = 500;

    //@ApiModelProperty(value = "返回标记：成功标记=0，失败标记=1")
    private Integer code;

    //@ApiModelProperty(value = "返回信息")
    private String msg;

    //@ApiModelProperty(value = "数据")
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> R<T> failed() {
        return restResult(null, FAIL, null);
    }

    private static <T> R<T> restResult(T data, Integer code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}