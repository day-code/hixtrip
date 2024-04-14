package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 支付信息
 * 
 * @author duay
 * @email
 * @date 2024-04-14 11:34:05
 */
@Data
@TableName("t_payment_info")
public class PaymentInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 订单id
	 */
	private String orderId;
	/**
	 * 交易流水号
	 */
	private String payTradeNo;
	/**
	 * 支付总金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 支付状态
	 */
	private String paymentStatus;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 确认时间
	 */
	private Date confirmTime;
	/**
	 * 回调内容
	 */
	private String callbackContent;
	/**
	 * 回调时间
	 */
	private Date callbackTime;


}
