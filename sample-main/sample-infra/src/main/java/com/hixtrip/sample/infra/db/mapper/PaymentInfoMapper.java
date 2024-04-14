package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.PaymentInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息
 * 
 * @author duay
 * @email
 * @date 2024-04-14 11:34:05
 */
@Mapper
public interface PaymentInfoMapper extends BaseMapper<PaymentInfoDO> {
	
}
