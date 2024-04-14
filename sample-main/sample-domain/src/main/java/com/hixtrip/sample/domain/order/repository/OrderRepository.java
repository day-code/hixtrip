package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

import java.math.BigDecimal;

/**
 *
 */
public interface OrderRepository {

    /**
     * 生成订单
     * @param order
     * @return
     */
    Boolean createOrder(Order order);

    /**
     * 商品skuId的价格
     * @param skuId
     * @return
     */
    BigDecimal getProductPrice(String skuId);

    /**
     * 处理回调
     * @param commandPay
     * @return
     */
    String payCallback(CommandPay commandPay);;
}
