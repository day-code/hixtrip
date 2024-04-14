package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表信息
 * 
 * @author duay
 * @email
 * @date 2024-04-14 11:34:06
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
	
}
