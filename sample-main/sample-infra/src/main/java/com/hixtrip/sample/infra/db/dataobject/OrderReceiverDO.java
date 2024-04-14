package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 收件人信息
 * 
 * @author duay
 * @email
 * @date 2024-04-14 11:34:06
 */
@Data
@TableName("t_order_receiver")
public class OrderReceiverDO implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 *
	 */
	private String userId;
	/**
	 *
	 */
	private String userName;
	/**
	 * 收货人姓名
	 */
	private String receiverName;
	/**
	 * 收货人电话
	 */
	private String receiverPhone;
	/**
	 * 收货人邮编
	 */
	private String receiverPostCode;
	/**
	 * 省份/直辖市
	 */
	private String receiverProvince;
	/**
	 * 城市
	 */
	private String receiverCity;
	/**
	 * 区
	 */
	private String receiverRegion;
	/**
	 * 详细地址
	 */
	private String receiverDetailAddress;
	/**
	 *
	 */
	private Integer isDefault;
	/**
	 * 修改时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 删除状态【0->未删除；1->已删除】
	 */
	private Integer delFlag;

}
