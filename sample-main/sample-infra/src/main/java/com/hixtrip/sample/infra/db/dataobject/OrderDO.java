package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 订单表信息
 * 
 * @author duay
 * @email
 * @date 2024-04-14 11:34:06
 */
@Data
@TableName("t_order")
public class OrderDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 订单号
	 */
	@TableId
	private String id;
	/**
	 * 购买人
	 */
	private String userId;
	/**
	 * SkuId
	 */
	private String skuId;
	/**
	 * 店铺id
	 */
	private String shopId;
	/**
	 * 购买数量
	 */
	private Integer amount;
	/**
	 * 购买金额
	 */
	private BigDecimal money;
	/**
	 * 购买时间
	 */
	private Date payTime;
	/**
	 * 支付状态
	 */
	private String payStatus;
	/**
	 * 删除标志（0代表存在 1代表删除）
	 */
	private Integer delFlag;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
