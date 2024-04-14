package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Autowired
    OrderDomainService orderDomainService;


    @Override
    public String order(CommandOderCreateDTO commandOderCreateDTO) {
        Order order = OrderConvertor.INSTANCE.doToDto(commandOderCreateDTO);
        orderDomainService.createOrder(order);
        return "创建订单成功";
    }

    public String payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = OrderConvertor.INSTANCE.doToDto(commandPayDTO);
        String status = orderDomainService.payCallback(commandPay);
        return status;
    }

}
