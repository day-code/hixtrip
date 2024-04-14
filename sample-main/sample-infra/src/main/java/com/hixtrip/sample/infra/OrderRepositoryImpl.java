package com.hixtrip.sample.infra;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.enums.PayStatusEnum;
import com.hixtrip.sample.infra.db.factory.PayHandlerFactory;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

/**
 * infra层是domain定义的接口具体的实现
 */
@Slf4j
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    PayHandlerFactory payHandlerFactory;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.doToDo(order);
        String skuId = orderDO.getSkuId();
        Integer amount = orderDO.getAmount();
        //计算购买金额
        BigDecimal amountDecimal = BigDecimal.valueOf(amount);
        orderDO.setMoney(amountDecimal.multiply(getProductPrice(skuId)));
        //先判断库存是否充足
        Integer inventory = inventoryRepository.getInventory(skuId);
        if (inventory >= amount) {
            //购买：使用lua让可售库存减少，预占库存增加
            Boolean seckill = inventoryRepository.sellProduct(skuId, amount);
            if (!seckill) {
                return false;
            }
            //to do 收件人信息等省略
            orderMapper.insert(orderDO);
        }
        return false;
    }

    /**
     * @param skuId
     * @return
     */
    public BigDecimal getProductPrice(String skuId) {
        Random random = new Random();
        // 生成一个介于0（包含）和100（不包含）之间的随机整数
        int randomInt = random.nextInt(100);
        // 将随机整数转换为BigDecimal
        BigDecimal randomBigDecimal = new BigDecimal(randomInt);
        return randomBigDecimal;
    }

    @Override
    public String payCallback(CommandPay commandPay) {
        String orderId = commandPay.getOrderId();
        String payStatus = commandPay.getPayStatus();
        OrderDO orderDO = orderMapper.selectById(orderId);
        if (PayStatusEnum.FAIL.getValue().equals(payStatus)) {
            return payHandlerFactory.triggerPayHandler(PayStatusEnum.FAIL, orderDO.getSkuId(), orderDO.getAmount());
        }
        LambdaUpdateWrapper<OrderDO> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        lambdaUpdateWrapper.eq(OrderDO::getId, orderId);
        lambdaUpdateWrapper.eq(OrderDO::getPayStatus, "0");
        lambdaUpdateWrapper.set(OrderDO::getPayStatus, "1");
        Integer updateNum = orderMapper.update(null, lambdaUpdateWrapper);
        if (updateNum == 1) {
            return payHandlerFactory.triggerPayHandler(PayStatusEnum.SUCCESS, orderDO.getSkuId(), orderDO.getAmount());
        }
        //幂等，支付重复
        return payHandlerFactory.triggerPayHandler(PayStatusEnum.REPEAT, orderDO.getSkuId(), orderDO.getAmount());
    }
}
